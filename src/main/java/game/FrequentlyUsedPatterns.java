package game;

import java.util.regex.Pattern;

public class FrequentlyUsedPatterns {
    public static Pattern WelcomePattern = Pattern.compile("^WELCOME TO ANOTHER EDITION OF THUNDERDOME!$");

    public static Pattern PlayerIDPattern = Pattern.compile("^WAIT FOR THE TOURNAMENT TO BEGIN ([\\d]*)$");

    public static Pattern ChallengePattern = Pattern.compile("^NEW CHALLENGE ([\\d]*) YOU WILL PLAY ([\\d]*) MATCH[ES]*$");
    public static Pattern ChallengeOverPattern = Pattern.compile("^END OF CHALLENGES$");

    public static Pattern RoundBeginPattern = Pattern.compile("^BEGIN ROUND ([\\d]*) OF ([\\d]*)$");
    static Pattern RoundEndPattern = Pattern.compile("^END OF ROUND ([\\d]*) OF ([\\d]*)$");
    static Pattern RoundEndNextPattern = Pattern.compile("^END OF ROUND ([\\d]*) OF ([\\d]*) WAIT FOR THE NEXT MATCH$");

    public static Pattern NewMatchPattern = Pattern.compile("^NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ([\\d]*)$");

    public static Pattern MoveServerPromptPattern = Pattern.compile("^MAKE YOUR MOVE IN GAME ([\\d\\w]*) WITHIN ([\\d.]*) SECOND[S]*: MOVE ([\\d]*) PLACE ([\\w+]*)$");
    public static Pattern GameMovePlayerPattern = Pattern.compile("^GAME ([\\d\\w]*) MOVE ([\\d]*) PLAYER ([\\d]*) ([\\w\\d ]*)$");
    public static Pattern GameForfeitedPattern = Pattern.compile("^GAME ([\\d\\w]*) MOVE ([\\d]*) PLAYER ([\\d]*) FORFEITED:([\\w ]*)$");
    public static Pattern GameLostPattern = Pattern.compile("^GAME ([\\d\\w]*) MOVE ([\\d]*) PLAYER ([\\d]*) LOST:([\\w ]*)$");
    public static Pattern GameOverPattern = Pattern.compile("^GAME ([\\d\\w]*) OVER PLAYER ([\\d]*) ([\\d\\w]*) PLAYER ([\\d]*) ([\\d\\w]*)$");

    static Pattern ExitPattern = Pattern.compile("^THANK YOU FOR PLAYING! GOODBYE$");

    public static Pattern PlacementPattern = Pattern.compile("^PLACED ([\\w+]*) AT ([\\d]*) ([\\d]*) ([\\d]*) ([\\d ]*)$");
    public static Pattern BuildPattern = Pattern.compile("^([\\w ]*) AT ([\\d]*) ([\\d]*) ([\\d]*)([\\w ]*)$");
}