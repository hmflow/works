package programmers;

import java.util.Arrays;

/**
 *
 *
 *
 * 제한사항 2 ≤ ability의 길이 ≤ 1,000,000 1 ≤ ability의 원소 ≤ 100 1 ≤ number ≤ 10,000
 * return 값이 10억 이하가 되도록 ability와 number가 주어집니다. 입출력 예 ability number result
 * [10, 3, 7, 2] 2 37 [1, 2, 3, 4] 3 26 입출력 예 설명 입출력 예 #1
 *
 * 문제 예시와 같습니다. 입출력 예 #2
 *
 * 첫 번째 사원과 두 번째 사원을 선발하여 공부를 시킨 후, 세 번째 사원과 네 번째 사원을 선발하여 공부를 시킵니다. 그 후 첫 번째
 * 사원과 두 번째 사원을 한번 더 선발해 공부를 시키면, 신입사원들의 능력치는 순서대로 6, 6, 7, 7이 되고, 이때가 능력치의 합이
 * 최소인 26이 됩니다.
 */
public class Pccp2_2_Rb {
	public int solution(int[] ability, int number) {
		if (number != 0) {
			Arrays.sort(ability);
			int sumData = ability[0] + ability[1];
			ability[0] = sumData;
			ability[1] = sumData;
			return solution(ability, number - 1);
		} else {
			return Arrays.stream(ability).sum();
		}
	}

}
