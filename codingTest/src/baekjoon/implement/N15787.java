package baekjoon.implement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

// https://www.acmicpc.net/problem/15787
// 기차가 어둠을 헤치고 은하수를
// 명령에 따라 좌석들을 배치한 후 동일한 좌석 배치를 가진 기차는 통과할 수 없는 문제
public class N15787 {
    // 기차를 나타낼 List
    static List<List<Integer>> list;
    public static void main(String[] args) throws Exception{
        // 동일한 좌석배치 여부 판단을 위해 Set 활용
        Set<String> set = new HashSet<>();

        // 입출력을 위한 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N = 기차의 수, M = 명령의 수
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 기차의 수 초기화
        list = new ArrayList<>(N);

        // 기차별 좌석 값 0으로 초기화 List 새로 생성해서 해야 주소값 다르게 적용됨
        // 좀 더 좋은 방법을 초기화할 수 있을 것 같음
        for(int i=0; i<N; i++){
            List<Integer> data = new ArrayList<>();
            for(int j =0; j<20; j++){
                data.add(0);
            }
            list.add(data);
        }

        // 명령 입력 및 파라미터 전달
        for(int i=0; i<M; i++){
            String[] params = br.readLine().split(" ");
            resultFunction(params);
        }

        // 각 기차에 대해 toString 적용하면 좌석 배치가 나타남
        for(int i=0; i<N; i++){
            set.add(list.get(i).toString());
        }
        bw.write(set.size()+"");
        bw.flush();
    }


    // 실제 명령을 처리하는 함수
    public static void resultFunction(String[] params){
        // 파라미터 0 : 명령 구분 코드, 1 : 명령받을 기차, 2 : 적용될 좌석 (3, 4번 명령의 경우 좌석 필요 없음)
        int c = Integer.parseInt(params[0]);
        int rtrain = Integer.parseInt(params[1])-1;
        int ridx   = params.length > 2 ? Integer.parseInt(params[2])-1 : 0;

        switch (c){
            case 1:
                list.get(rtrain).set(ridx, 1);
                break;
            case 2:
                list.get(rtrain).set(ridx, 0);
                break;
            case 3:
                list.get(rtrain).remove(19);
                list.get(rtrain).add(0, 0);
                break;
            case 4:
                list.get(rtrain).remove(0);
                list.get(rtrain).add(0);
                break;
        }
    }
}
