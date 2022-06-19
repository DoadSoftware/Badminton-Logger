package com.badminton.broadcaster;

import java.io.PrintWriter;

import com.badminton.model.BadmintonMatch;
import com.badminton.model.Scene;
//import com.badminton.model.Stats;
import com.badminton.util.BadmintonUtil;


public class Doad extends Scene {
	
	private String status;

	public Doad() {
		super();
	}

	public Doad(String scene_path) {
		super(scene_path);
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void populateScoreBug(PrintWriter print_writer,BadmintonMatch match, String viz_sence_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			//System.out.println("on Strike player id-" + match.getOnStrikePlayerId());
			//System.out.println("Home Player Name-"+ match.getMatch().getHomePlayer().getSurname() + " Home Team Name-"+ match.getMatch().getHomePlayer().getTeam().getShortname());
			//System.out.println("Away Player Name-"+ match.getMatch().getAwayPlayer().getSurname() + " Away Team Name-"+ match.getMatch().getAwayPlayer().getTeam().getShortname());
			
			/*print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$PLAYERNAME_01*GEOM*TEXT SET " + match.getMatch().getHomePlayer().getSurname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$COUNTRY_01*GEOM*TEXT SET " + match.getMatch().getHomePlayer().getTeam().getShortname() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$PLAYERNAME_02*GEOM*TEXT SET " + match.getMatch().getAwayPlayer().getSurname() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$COUNTRY_02*GEOM*TEXT SET " + match.getMatch().getHomePlayer().getTeam().getShortname() + "\0");
			
			print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$TOTAL_ROUNDS*GEOM*TEXT SET " + match.getMatch().getNumberOfFrames() + "\0");
			
			if(match.getOnStrikePlayerId() == match.getMatch().getHomePlayerId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$playing_arrow*ACTIVE SET 1 " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$playing_arrow*FUNCTION*Omo*vis_con SET 0 \0");
			}else if(match.getOnStrikePlayerId() == match.getMatch().getAwayPlayerId()) {
				print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$playing_arrow*ACTIVE SET 1 " + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$playing_arrow*FUNCTION*Omo*vis_con SET 1 \0");
			}else {
				print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$playing_arrow*ACTIVE SET 0 " + "\0");
			}
			
			
			for(Stats stat : match.getStats()) {
				if(stat.getStatType().equalsIgnoreCase(BadmintonUtil.SCORES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$POINTS_01*GEOM*TEXT SET " + stat.getHomeStatCount() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$POINTS_02*GEOM*TEXT SET " + stat.getAwayStatCount() + "\0");
					
					if(stat.getHomeStatCount() == 0 && stat.getAwayStatCount() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$RemainingValue*GEOM*TEXT SET " + "147" + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$RemainingValue*GEOM*TEXT SET " + 
								(147 -(stat.getHomeStatCount() + stat.getAwayStatCount())) + "\0");
					}
					
					if(stat.getHomeStatCount() == 0 && stat.getAwayStatCount() == 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AheadValue*GEOM*TEXT SET " + "0" + "\0");
					}else if(stat.getHomeStatCount() > stat.getAwayStatCount()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AHEAD*GEOM*TEXT SET " + "AHEAD" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AheadValue*GEOM*TEXT SET " + 
								(stat.getHomeStatCount() - stat.getAwayStatCount()) + "\0");
					}else if(stat.getHomeStatCount() < stat.getAwayStatCount()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AHEAD*GEOM*TEXT SET " + "BEHIND" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AheadValue*GEOM*TEXT SET " + 
								(stat.getAwayStatCount() - stat.getHomeStatCount()) + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AheadValue*GEOM*TEXT SET " + "0" + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$AHEAD_REMAINING_LEFT_GRP$AHEAD*GEOM*TEXT SET " + "EQUAL" + "\0");
					}
				}
				
				
				if(stat.getStatType().equalsIgnoreCase(BadmintonUtil.FRAMES)) {
					print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$WIN_01*GEOM*TEXT SET " + stat.getHomeStatCount() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$Mainb_grp$WIN_02*GEOM*TEXT SET " + stat.getAwayStatCount() + "\0");
				}
			}*/
		}
			this.status = BadmintonUtil.SUCCESSFUL;
	}

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	/*public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic)
	{
		//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
		
		switch(whichGraphic) {
		case "SCOREBUG":
			//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SCORE_BUG_Snooker START \0");
			this.status = BadmintonUtil.SUCCESSFUL;
			break;
		}	
	}*/
}
