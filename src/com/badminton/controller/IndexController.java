package com.badminton.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

import com.badminton.broadcaster.Doad;
import com.badminton.containers.Configurations;
import com.badminton.model.BadmintonMatch;
import com.badminton.model.Match;
import com.badminton.model.Scene;
import com.badminton.model.Sets;
import com.badminton.model.Stats;
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
	public static Doad this_doad;
	public static BadmintonMatch session_match;
	public static PrintWriter print_writer;
	String selectedBroadcaster,which_graphics_onscreen,viz_scene_path;
	
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
			@RequestParam(value = "selectedMatch", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "session_socket", required = false, defaultValue = "") String Socket,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") int vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene)
			throws UnknownHostException,JAXBException, IOException,IllegalAccessException,InvocationTargetException, InterruptedException
	{
		selectedBroadcaster = select_broadcaster;
		session_Configurations = new Configurations(selectedMatch, select_broadcaster, vizIPAddresss, vizPortNumber, vizScene);
		
		//viz_scene_path = vizScene;
		
		session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
		print_writer = new PrintWriter(session_socket.getOutputStream(),true);
		
		//new Scene(vizScene).scene_load(print_writer);
		viz_scene_path = "D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_GBPL\\Scenes\\Scorebug.sum";
		new Scene(viz_scene_path).scene_load(new PrintWriter(session_socket.getOutputStream(),true),selectedBroadcaster,viz_scene_path);
		
		which_graphics_onscreen = "";
		this_doad = new Doad();
				
		if(new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch).exists()) {
			session_match = populateMatchVariables((BadmintonMatch) JAXBContext.newInstance(BadmintonMatch.class).createUnmarshaller().unmarshal(
				new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch)));
		} else {
			session_match = new BadmintonMatch(new Match(Integer.valueOf(selectedMatch.toUpperCase().replace(".XML", ""))));
			
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + selectedMatch));
		}
			
		//session_match.setMatch_file_timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		
		model.addAttribute("session_match", session_match);
		model.addAttribute("session_socket", session_socket);
		model.addAttribute("select_broadcaster", select_broadcaster);
		
		return "logger";
	}
	

	@RequestMapping(value = {"/save_stats_data","/end_set"}, method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String uploadFormDataToSessionObjects(MultipartHttpServletRequest request)
					throws IllegalAccessException, InvocationTargetException, IOException, JAXBException
	{	
		if (request.getRequestURI().contains("save_stats_data")) {
			
			List<Sets> this_sets = new ArrayList<Sets>();
			for(int i=1;i<=3;i++) {
				for(Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
					for(Sets set : this_sets) {
						if(!entry.getKey().contains("_btn") && !entry.getKey().contains("select_")) {
						
						}
					}
				}
			}
			
			List<Stats> this_stats = new ArrayList<Stats>();
			
			this_stats.add(new Stats(1, BadmintonUtil.SETS));
			this_stats.add(new Stats(2, BadmintonUtil.SCORES));
			this_stats.add(new Stats(3, BadmintonUtil.FOREHAND_WINNER));
			this_stats.add(new Stats(4, BadmintonUtil.FOREHAND_ERRORS));
			this_stats.add(new Stats(5, BadmintonUtil.BACKHAND_WINNER ));
			this_stats.add(new Stats(6, BadmintonUtil.BACKHAND_ERRORS));
			
			for (Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
				for(Stats stat : this_stats) {
					if(!entry.getKey().contains("_btn") && !entry.getKey().contains("select_")) { // Ignore buttons. Just take statistics text box values
						
						if(entry.getKey().toUpperCase().contains(stat.getStatType())) {
							//increment the value in the Home and away stat_count
							if(entry.getKey().toUpperCase().contains(BadmintonUtil.HOME)){
								stat.setHomeStatCount(Integer.valueOf(entry.getValue()[0]));
							} 
							else if(entry.getKey().toUpperCase().contains(BadmintonUtil.AWAY)) {
								stat.setAwayStatCount(Integer.valueOf(entry.getValue()[0]));
							}
						}
					}
				}
	   		}
			session_match.setStats(this_stats);
			JAXBContext.newInstance(BadmintonMatch.class).createMarshaller().marshal(session_match, 
					new File(BadmintonUtil.BADMINTON_DIRECTORY + BadmintonUtil.MATCHES_DIRECTORY + session_match.getMatch().getMatchId() + BadmintonUtil.XML));
		}
		else {
			session_match.setSets()
		}
		return JSONObject.fromObject(session_match).toString();
	}

	@RequestMapping(value = {"/processBadmintonProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processSnookerProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException, InterruptedException
	{	
		switch (whatToProcess.toUpperCase()) {
		
		/*case "READ-MATCH-AND-POPULATE":
			if(!valueToProcess.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
					new File(SnookerUtil.SNOOKER_DIRECTORY + SnookerUtil.MATCHES_DIRECTORY + session_match.getMatch_file_timestamp()).lastModified())))
			{
				session_match = populateMatchVariables((SnookerMatch) JAXBContext.newInstance(SnookerMatch.class).createUnmarshaller().unmarshal(
				new File(SnookerUtil.SNOOKER_DIRECTORY + SnookerUtil.MATCHES_DIRECTORY + session_match.getMatch_file_timestamp())));
				
				session_match.setMatch_file_timestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
						new File(SnookerUtil.SNOOKER_DIRECTORY + SnookerUtil.MATCHES_DIRECTORY + session_match.getMatch_file_timestamp()).lastModified()));
				
					this_doad.populateScoreBug(print_writer, session_match, viz_scene_path);
					
				return JSONObject.fromObject(session_match).toString();
			}
			else {
				return JSONObject.fromObject(null).toString();
			}*/
		
		case "POPULATE-SCOREBUG":
			switch (selectedBroadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_EVEREST":
				
				this_doad.populateScoreBug(print_writer,session_match, viz_scene_path);
			}
			return JSONObject.fromObject(this_doad).toString();
		
		case "ANIMATE-IN-SCOREBUG":
			switch (selectedBroadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_EVEREST":
				this_doad.processAnimation(print_writer, "In", "START", selectedBroadcaster);
				which_graphics_onscreen = "SCORE_BUG_Badminton";
			}
			return JSONObject.fromObject(this_doad).toString();
		
		
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
			match = badmintonService.getMatch(match.getMatchId());
			
				if(match.getHomeFirstPlayer() != null) {
					match.setHomePlayer(badmintonService.getPlayer(match.getHomeFirstPlayer()));
					match.getHomePlayer().setTeam(badmintonService.getTeam(match.getHomePlayer().getTeamId()));
				}
				
				if(match.getHomeSecondPlayer() != null) {
					match.setHomePlayer1(badmintonService.getPlayer(match.getHomeSecondPlayer()));
					match.getHomePlayer1().setTeam(badmintonService.getTeam(match.getHomePlayer1().getTeamId()));
				}
				
				if(match.getAwayFirstPlayer() != null) {
					match.setAwayPlayer(badmintonService.getPlayer(match.getAwayFirstPlayer()));
					match.getAwayPlayer().setTeam(badmintonService.getTeam(match.getAwayPlayer().getTeamId()));
				}
				
				if(match.getAwaySecondPlayer() != null) {
					match.setAwayPlayer1(badmintonService.getPlayer(match.getAwaySecondPlayer()));
					match.getAwayPlayer1().setTeam(badmintonService.getTeam(match.getAwayPlayer1().getTeamId()));
				}
				
			}
		return match;
	}
	
}