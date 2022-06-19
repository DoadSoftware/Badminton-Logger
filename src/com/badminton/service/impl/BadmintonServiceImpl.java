package com.badminton.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.badminton.dao.BadmintonDao;
import com.badminton.model.Match;
import com.badminton.model.Player;
import com.badminton.model.Team;
import com.badminton.service.BadmintonService;

@Service("badmintonService")
@Transactional
public class BadmintonServiceImpl implements BadmintonService {

 @Autowired
 private BadmintonDao badmintonDao;
 
@Override
public Player getPlayer(int player_id) {
	return badmintonDao.getPlayer(player_id);
}

@Override
public Team getTeam(int team_id) {
	return badmintonDao.getTeam(team_id);
}

@Override
public List<Match> getAllMatches() {
	return badmintonDao.getAllMatches();
}

@Override
public Match getMatch(int matchID) {
	return badmintonDao.getMatch(matchID);
}

}