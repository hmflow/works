import java.util.*;
/**
 * 로봇이 아래 그림과 같이 2차원 평면의 원점 (0, 0)에 서있습니다.
	 
	이 로봇은 x축 방향, 혹은 y축 방향으로만 움직일 수 있으며, 알파벳으로 명령을 내릴 수 있습니다. 명령을 내릴 때 사용하는 알파벳은 'L', 'R', 'U', 'D'의 4가지이며, 'L'은 x축 방향으로 -1만큼, 'R'은 x축 방향으로 +1만큼, 'U'는 y축 방향으로 +1만큼, 'D'는 y축 방향으로 -1 만큼 이동하라는 의미입니다.
	
	로봇에게 내린 명령이 순서대로 들어있는 문자열 commands가 매개변수로 주어질 때, 주어진 명령을 모두 수행한 후의 로봇 위치를 return 하도록 solution 함수를 완성해주세요.
	
	□ 매개변수 설명
	
	로봇에게 내린 명령이 순서대로 들어있는 문자열 commands가 solution 함수의 매개변수로 주어집니다.
	
	commands는 알파벳 대문자 'L', 'R', 'U', 'D'로만 이루어진 문자열이며, 길이는 1 이상 100 이하입니다.
	□ return 값 설명
	
	주어진 명령을 모두 수행한 후의 로봇 위치를 return 해주세요.
	
	[x축 좌표, y축 좌표] 형태로 로봇의 최종 위치를 배열에 담아 return 해주세요.
	□ 예시
	
	commands	return 
	"URDDL"	[0, -1]
	□ 예시설명
	
	"URDDL"명령에 따라 (0, 0)에 있는 로봇은 다음과 같이 움직입니다.
	
	[0, 0] → [0, 1] → [1, 1] → [1, 0] → [1, -1] → [0, -1]
	따라서 로봇의 최종 위치는 [0, -1]입니다.
 * */
public class ProgrammerCosPro1_rb {
	int[] dirY = { 0, 1, 0, -1 };
	int[] dirX = { 1, 0, -1, 0 };

	public int[] solution(String command) {
		int nowloc = 0;
		int[] answer = { 0, 0 }; 
		for (int i = 0; i < command.length(); i++) {
			String nowChar = command.charAt(i) + "";
			
			int addVal = "LB".indexOf(nowChar) != -1 ? -1 : 1;
			if (addVal == -1) {
				nowloc += addVal;
				nowloc = nowloc < 0 ? 3 : nowloc > 3 ? 0 : nowloc;
			} else {
				for (int v = 0; v <= 1; v++) {
					int nowVal = dirY[nowloc] == 0 ? dirX[nowloc] : dirY[nowloc];
					answer[v] += nowVal == 0 ? 0 : nowVal * addVal;
				}
			}
		}
		return answer;
	}
 
}
