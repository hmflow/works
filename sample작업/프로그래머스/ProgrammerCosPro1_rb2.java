import java.util.*;
/**  
 * 음;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 * 여러번 실행시키다가 갑자기 통과되네;;;;;;;;;;;;;;;;;;;;;;;;;;
 * 
 * [PCCP 모의고사 #2] 2번 - 신입사원 교육
문제 설명
산업스파이 민수는 A회사에 위장 취업했습니다. 이를 모르는 민수의 상사는 신입사원 교육 중 일부를 민수에게 맡겼습니다. 
민수가 맡은 임무는 신입사원 중 2명을 선발하고 선발된 2명이 같이 공부하게 하는 것입니다. 
모든 신입사원들의 능력치는 정수로 표현되어 있는데, 2명의 신입사원이 같이 공부하면 서로의 능력을 흡수하여 
두 신입사원의 능력치는 공부 전 두 사람의 능력치의 합이 됩니다. 
즉, 능력치가 3과 7인 두 사원이 같이 공부하면 두 사원의 능력치가 모두 10이 됩니다. 
선발한 2인의 교육이 끝나면 민수는 다시 2인을 선발하여 교육을 진행할 수도 있습니다.
 이때 한번 민수에게 선발된 사원이 다시 선발될 수도 있습니다. 
 민수가 교육한 신입사원들을 제외한 다른 신입사원들의 능력치는 변하지 않습니다.

민수는 산업스파이이기 때문에 교육 후 모든 신입사원들의 능력치의 합을 최소화하고 싶습니다. 
예를 들어, 신입사원들의 능력치가 순서대로 10, 3, 7, 2이며, 민수가 2번 교육을 해야 한다면,
 첫 번째 교육은 두 번째 사원과 네 번째 사원을 선발하면 두 사원의 능력치가 5가 되어 신입사원들의 능력치가 10, 5, 7, 5가 됩니다.
 두 번째 교육도 두 번째 사원과 네 번째 사원을 선발하면 신입사원들의 능력치는 순서대로 10, 10, 7, 10이 됩니다. 
 이 경우가 교육 후 모든 신입사원들의 능력치의 합이 37로 최소가 되는 경우입니다.

신입사원들의 능력치를 나타내는 정수 배열 ability와 민수가 교육을 진행해야 하는 횟수를 나타내는 정수 number가 주어졌을 때,
 교육 후 모든 신입사원들의 능력치의 합의 최솟값을 return 하는 solution 함수를 완성하세요.

제한사항
2 ≤ ability의 길이 ≤ 1,000,000
1 ≤ ability의 원소 ≤ 100
1 ≤ number ≤ 10,000
return 값이 10억 이하가 되도록 ability와 number가 주어집니다.
입출력 예
ability	number	result
[10, 3, 7, 2]	2	37
[1, 2, 3, 4]	3	26
입출력 예 설명
입출력 예 #1

문제 예시와 같습니다.
입출력 예 #2

첫 번째 사원과 두 번째 사원을 선발하여 공부를 시킨 후, 세 번째 사원과 네 번째 사원을 선발하여 공부를 시킵니다. 그 후 첫 번째 사원과 두 번째 사원을 한번 더 선발해 공부를 시키면, 신입사원들의 능력치는 순서대로 6, 6, 7, 7이 되고, 이때가 능력치의 합이 최소인 26이 됩니다.
 * */
public class ProgrammerCosPro1_rb {
    public int solution(int[] ability, int number) {

        if(number != 0) {
            Arrays.sort(ability);
            int sumData = ability[0] + ability[1];      
            ability[0] = sumData;
            ability[1] = sumData;
            return solution(ability, number-1);
        }else { 
            return Arrays.stream(ability).sum();
        }   
    }  
}

