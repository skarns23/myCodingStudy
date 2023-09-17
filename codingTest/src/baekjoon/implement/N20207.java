package baekjoon.implement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

// https://www.acmicpc.net/problem/20207
// 달력
// 왜 57%에서 틀리지 -> 경계선 값에 대한 처리가 되지 않음 중복 구간 없이 경계 값에서 이어지는 일정의 경우 고려하지 못했었음
public class N20207 {
    // 배열의 크기를 정의 367 이유는 앞뒤로의 일정을 확인하는 로직에서 indexOfBound Exception 발생시키지 않기 위해서
    static final int MAX_SIZE = 367;

    // 일정을 담을 배열 arr
    static int[] arr = new int [MAX_SIZE];
    public static void main(String[] args) throws Exception{
        // 입출력을 위한 버퍼 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 연속된 일정 블럭을 담을 List 생성
        List<int[]> resultList = new ArrayList<>();

        // 일정의 수를 담는 int 변수 N
        int N = Integer.parseInt(st.nextToken());

        // 2차원 배열에 일정의 일정의 순서 / 시작일 / 종료일을 담기 위해 이차원 배열로 선언
        int[][] valArr = new int[N][2];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            valArr[i][0] = start;
            valArr[i][1] = end;
        }

        // 일정에 대한 정렬을 진행 시작일 기준 ASC, 종료일 기준 DESC 진행
        // 2차원 배열에 대한 정렬을 진행할 떄는 Comparator 인터페이스의 compare method 재정의 해줘야함
        // 혹은 다른  클래스를 생성한 후 정렬을 진행하는 경우 Comparable 인터페이스를 implements 받은 후 compareTo 메소드 구현을 통해 진행 가능
        Arrays.sort(valArr, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0])
                    return o2[1] - o1[1];
                return o1[0] - o2[0];
            }
        });

        // 정렬된 일정에 대해 각 구간의 중복된 일정을 연산하는 로직  
        int idx = -1;
        for(int i = 0; i < N; i++){
            int start = valArr[i][0];
            int end = valArr[i][1];
            int maxVal = count(start, end);

            // 시작일과 종료일 사이의 최대 일정이 1이며 시작일 이전에 일정이 있는 없고, 종료일 이후 일정이 없는 경우
            // 새로운 일정 블록으로 List에  추가
            if(maxVal == 1 && !(0 < arr[start - 1] || 0 < arr[end + 1])){
                resultList.add(new int[]{start, end, maxVal});
                idx++;
                continue;
            }
            // 새로운 블럭을 추가하는 경우가 아니면 시작일, 종료일, 최대 일정 값 갱신
            int[] tmpArr = resultList.get(idx);
            tmpArr[0] = Math.min(start, tmpArr[0]);
            tmpArr[1] = Math.max(end, tmpArr[1]);
            tmpArr[2] = Math.max(maxVal, tmpArr[2]);
        }

        // 이후 List에 넣어놓은 블럭을 통해 필요한 최대 코팅지를 계산
        int totalSum = 0;
        for(int[] tmp : resultList){
            totalSum += (tmp[1] - tmp[0] + 1) * tmp [2];
        }
        bw.write(totalSum+"");
        bw.flush();

    }

    // 시작일과 종료일에 사이의 일정을 추가하는 함수
    // 그 중 가장 큰 일정 값을 반환함
    public static int count(int s, int e){
        int maxVal = 0;

        for(int i = s; i <= e; i++){
            arr[i]++;
            maxVal = Math.max(maxVal,arr[i]);
        }
        return maxVal;
    }
}
