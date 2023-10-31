package programmers;
import java.util.Arrays;

/**
 *
	컴퓨터공학과에서는 실습용 로봇을 이용해서 로봇 프로그래밍을 학습합니다.
	실습용 로봇은 입력된 명령에 따라 x좌표와 y좌표로 표현되는 2차원 좌표 평면 위를 이동합니다.
	하나의 명령은 하나의 문자로 주어지며 각 명령어에 따라 로봇이 수행하는 일은 다음과 같이 네 종류입니다.

	'R': 로봇이 오른쪽으로 90도 회전합니다.
	'L': 로봇이 왼쪽으로 90도 회전합니다.
	'G': 로봇이 한 칸 전진합니다.
	'B': 로봇이 한 칸 후진합니다.
	명령어는 각각의 명령들이
	모인 하나의 문자열로 주어지며, 차례대로 수행됩니다.

	로봇은 처음에 (0, 0) 위치에 +y 축을 향하여 놓여 있습니다.

	다음 그림은 번호 순서대로 명령어 "GRGLGRG"의 과정을 보여줍니다.

 로봇에 입력된 명령어를 순서대로 담고 있는 문자열 command가 주어집니다.
 로봇이 주어진 명령어들을 순서대로 모두 수행한 뒤 도착한 최종 위치의
 좌푯값 x, y를 순서대로 배열에 담아서 return 하도록 solution 함수를 완성해주세요.

	제한사항
	1 ≤ commad의 길이 ≤ 1,000,000
	command는 'R', 'L', 'G', 'B'으로 구성된 문자열입니다.
	command에 들어있는 문자 하나하나가 각 명령을 나타내며, 문자열에 먼저 등장하는 명령을 먼저 수행해야 합니다.
 * 입출력 예
	command	result
	"GRGLGRG"	[2, 2]
	"GRGRGRB"	[2, 0]
	입출력 예 설명
	입출력 예 #1

	문제 예시와 같습니다.
	입출력 예 #2

	로봇이 이동한 좌표는 (0, 0) → (0, 1) → (1, 1) → (1, 0) → (2, 0) 입니다.
 *
 * */
public class Pccp2_1_Rb {

	int[] dirY = { 0, 1, 0, -1 };
	int[] dirX = { 1, 0, -1, 0 };
	public int[] solution(String command) {
		int nowloc = 0;
		int[] answer = { 0, 0 };
		for (int i = 0; i < command.length(); i++) {
			String nowChar = command.charAt(i) + "";
			int addVal = Arrays.asList("L", "B").contains(nowChar) ? -1 : 1;
			if (Arrays.asList("R", "L").contains(nowChar)) {
				nowloc += addVal;
				nowloc = nowloc < 0 ? 3 : nowloc > 3 ? 0 : nowloc;
			} else {
				for (int v = 0; v <= 1; v++) {
					int nowVal = v == 0 ? dirY[nowloc] : dirX[nowloc];
					answer[v] += nowVal == 0 ? 0 : nowVal * addVal;
				}
			}
		}
		return answer;
	}

}
