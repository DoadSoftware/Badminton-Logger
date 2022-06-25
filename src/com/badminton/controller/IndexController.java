package com.badminton.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.badminton.containers.Configurations;
import com.badminton.model.BadmintonMatch;
import com.badminton.model.Match;
import com.badminton.model.Player;
import com.badminton.model.Set;
import com.badminton.model.Stats;
import com.badminton.model.Team;
import com.badminton.service.BadmintonService;
import com.badminton.util.BadmintonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	BadmintonService badmintonService;
	public static Configurations session_Configurations;
	public static Socket session_socket;
	public static BadmintonMatch session_match;
	public static String selectedMatch;
	public static String selectedBroadcaster;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException, IOException  
	{
		model.addAttribute("session_viz_scenes", new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.SCENES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".via") && pathname.isFile();
		    }
		}));
		model.addAttribute("match_files", new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
		if(new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.CONFIGURATIONS_DIRECTORY + BadmintonUtil.LOGGER_XML).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(Configurations.class).createUnmarshaller().unmarshal(
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.CONFIGURATIONS_DIRECTORY + BadmintonUtil.LOGGER_XML));
		} else {
			session_Configurations = new Configurations();
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.CONFIGURATIONS_DIRECTORY + BadmintonUtil.LOGGER_XML));
		}
	
		model.addAttribute("session_Configurations",session_Configurations);
		
		return "initialise";
	}

	@RequestMapping(value = {"/logger"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String loggerPage(ModelMap model, MultipartHttpServletRequest request,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "selectedMatch", required = false, defaultValue = "") String match,
			@RequestParam(value = "session_socket", required = false, defaultValue = "") String Socket)
			throws UnknownHostException,JAXBException, IOException,IllegalAccessException,InvocationTargetException, InterruptedException
	{
		selectedMatch = match;
		selectedBroadcaster = select_broadcaster;
		session_Configurations = new Configurations(selectedMatch, select_broadcaster);
				
		if(new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch).exists()) {
			session_match = populateMatchVariables((BadmintonMatch) JAXBContext.newInstance(BadmintonMatch.class).createUnmarshaller().unmarshal(
				new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch)));
		} else {
			session_match = new BadmintonMatch(new Match(Integer.valueOf(selectedMatch.toUpperCase().replace(".XML", ""))));
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch));
		}
		session_match.setDatabase_file_timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		
		model.addAttribute("session_match", session_match);
		model.addAttribute("session_socket", session_socket);
		model.addAttribute("select_broadcaster", select_broadcaster);
		
		return "logger";
	}
	
	@RequestMapping(value = {"/save_stats_data","/start_set","/end_set","/reset_set","/reset_all"}, method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String uploadFormDataToSessionObjects(MultipartHttpServletRequest request)
					throws IllegalAccessException, InvocationTargetException, IOException, JAXBException
	{	
		if (request.getRequestURI().contains("end_set")) {

			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				if(!entry.getKey().contains("_btn") && !entry.getKey().contains("select_")) { // Ignore buttons. Just take statistics text box values
					if(entry.getKey().toUpperCase().contains(BadmintonUtil.HOME)){
						session_match.setHomeTeamSetsWon(Integer.valueOf(entry.getValue()[0]));
					} else if(entry.getKey().toUpperCase().contains(BadmintonUtil.AWAY)) {
						session_match.setAwayTeamSetsWon(Integer.valueOf(entry.getValue()[0]));
					}
				}
	   		}
			if(session_match.getSets() != null && session_match.getSets().size() > 0) {
				session_match.getSets().get(session_match.getSets().size() - 1).setStatus(BadmintonUtil.END);
			}
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + 
					session_match.getMatch().getMatchId() + BadmintonUtil.XML));
		
		}else if (request.getRequestURI().contains("reset_set")) {
			if(session_match.getSets() != null || session_match.getSets().size() > 0) {
				session_match.getSets().remove(session_match.getSets().size() - 1);
			}
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + 
					session_match.getMatch().getMatchId() + BadmintonUtil.XML));
			
		}else if (request.getRequestURI().contains("reset_all")) {
			session_match = new BadmintonMatch(new Match(Integer.valueOf(selectedMatch.toUpperCase().replace(".XML", ""))));
			
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + 
					session_match.getMatch().getMatchId() + BadmintonUtil.XML));
			
		}else if (request.getRequestURI().contains("start_set")) {
			
			if(session_match.getSets() == null || session_match.getSets().size() <= 0) {
				session_match.setSets(new ArrayList<Set>());
			}
			session_match.getSets().add(new Set(session_match.getSets().size() + 1, BadmintonUtil.START, 
					session_match.getMatch().getHomePlayers(), session_match.getMatch().getAwayPlayers(), session_match.getStats()));
			
		} else if (request.getRequestURI().contains("save_stats_data")) {
			
			List<Stats> this_stats = new ArrayList<Stats>();
			
			this_stats.add(new Stats(1, BadmintonUtil.FOREHAND_WINNER));
			this_stats.add(new Stats(2, BadmintonUtil.FOREHAND_ERRORS));
			this_stats.add(new Stats(3, BadmintonUtil.BACKHAND_WINNER ));
			this_stats.add(new Stats(4, BadmintonUtil.BACKHAND_ERRORS));
			//this_stats.add(new Stats(5, BadmintonUtil.GOLDEN));
			
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				for(Stats stat : this_stats) {
					if(!entry.getKey().contains("_btn") && !entry.getKey().contains("select_")) { // Ignore buttons. Just take statistics text box values
						if(entry.getKey().toUpperCase().contains(BadmintonUtil.HOME)){
							if(entry.getKey().toUpperCase().contains(stat.getStatType())) {
								stat.setHomeStatCount(Integer.valueOf(entry.getValue()[0]));
							} else if (entry.getKey().toUpperCase().contains(BadmintonUtil.SCORES)) {
								if(session_match.getSets() != null && session_match.getSets().size() > 0) 
									session_match.getSets().get(session_match.getSets().size() - 1).setHomeTeamTotalScore(Integer.valueOf(entry.getValue()[0]));
							}
						} else if(entry.getKey().toUpperCase().contains(BadmintonUtil.AWAY)) {
							if(entry.getKey().toUpperCase().contains(stat.getStatType())) {
								stat.setAwayStatCount(Integer.valueOf(entry.getValue()[0]));
							} else if (entry.getKey().toUpperCase().contains(BadmintonUtil.SCORES)) {
								if(session_match.getSets() != null && session_match.getSets().size() > 0) 
									session_match.getSets().get(session_match.getSets().size() - 1).setAwayTeamTotalScore(Integer.valueOf(entry.getValue()[0]));
							}
						}
					}
				}
	   		}
			session_match.setStats(this_stats);
			if(session_match.getSets() != null && session_match.getSets().size() > 0) {
				session_match.getSets().get(session_match.getSets().size() - 1).setStats(this_stats);
			}
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + session_match.getMatch().getMatchId() + BadmintonUtil.XML));
		}
		return JSONObject.fromObject(session_match).toString();
	}

	@RequestMapping(value = {"/processBadmintonProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processBadmintonProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException, InterruptedException
	{	

		switch (whatToProcess.toUpperCase()) {
		case "READ-DATABASE-AND-POPULATE":
			if(!valueToProcess.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.DATABASE_DIRECTORY + BadmintonUtil.DATABASE_FILE).lastModified())))
			{
				session_match = populateMatchVariables((BadmintonMatch) JAXBContext.newInstance(BadmintonMatch.class).createUnmarshaller().unmarshal(
						new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch)));
				
				session_match.setDatabase_file_timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
						new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.DATABASE_DIRECTORY + BadmintonUtil.DATABASE_FILE).lastModified()));

				return JSONObject.fromObject(session_match).toString();
			}
			else {
				return JSONObject.fromObject(null).toString();
			}
			
		case "LOAD_MATCHES":
			
			List<Match> matches = new ArrayList<Match>();
			for(Match match : badmintonService.getAllMatches()) {
				matches.add(populateMatchVariables(match));
			}
			return JSONArray.fromObject(matches).toString();
			
		case BadmintonUtil.LOAD_MATCH: 
			return JSONObject.fromObject(populateMatchVariables(session_match)).toString();
			
		case "ON-STRIKE_PLAYER":
			session_match.setOnStrikePlayerId(Integer.valueOf(valueToProcess));
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + session_match.getMatch().getMatchId() + BadmintonUtil.XML));
			return JSONObject.fromObject(session_match).toString();
			
		case "GOLDEN-POINTS_PLAYER":
			session_match.setGoldenPointsPlayerId(Integer.valueOf(valueToProcess));
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + session_match.getMatch().getMatchId() + BadmintonUtil.XML));
			return JSONObject.fromObject(session_match).toString();
			
		default:
			return JSONObject.fromObject(null).toString();
		}
	}
	
	public BadmintonMatch populateMatchVariables(BadmintonMatch match)
	{
		if(match.getMatch() != null && match.getMatch().getMatchId() > 0) {
			match.setMatch(populateMatchVariables(match.getMatch()));
		}
		return match;
	}
	public Match populateMatchVariables(Match match)
	{
		if(match != null && match.getMatchId() > 0) {
			
			List<Player> players = new ArrayList<Player>();
			Team team = null;
			match = badmintonService.getMatch(match.getMatchId());
			
			if(match.getHomeFirstPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getHomeFirstPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			if(match.getHomeSecondPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getHomeSecondPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			if(match.getHomeThirdPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getHomeThirdPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			match.setHomePlayers(players);
			if(team != null)
				match.setHomeTeam(team);
			
			players = new ArrayList<Player>();
			team = null;
			if(match.getAwayFirstPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getAwayFirstPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			if(match.getAwaySecondPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getAwaySecondPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			if(match.getAwayThirdPlayerId() != null) {
				players.add(badmintonService.getPlayer(match.getAwayThirdPlayerId()));
				team = badmintonService.getTeam(players.get(players.size() - 1).getTeamId());
				players.get(players.size() - 1).setTeam(team);
			}
			match.setAwayPlayers(players);
			if(team != null)
				match.setAwayTeam(team);
			
		}
		return match;
	}
	
}