package baekjoon.implement;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

// https://www.acmicpc.net/problem/16926
// 배열 돌리기
// 크기 N * M 인 배열을 반시계 방향으로 돌리는 문제
public class N16926 {

    // 총 사각형의 개수는 min(N, M) / 2 -> 제약 조건으로 min(N, M) % 2 = 0 이기 떄문에 가능
    // 각 사각형의 시작점의 위치는 사각형의 depth -> (depth, depth)
    // 각 depth에 있는 사각형의 row와 col의 범위는 다음과 같음
    // row_max = N - depth - 1 / col_max = M - depth - M (depth, row_max), (depth, col_max)
    static int[][] arr;

    public static void main(String[] args) throws Exception {
        // 입출력을 위한 BufferedReader 및 BufferedWriter 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 보다 쉬운 입력을 위한 StringTokenizer 사용
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력받을 사각형의 행, 컬럼 값 및 로테이션 횟수 초기화
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        // static 으로 선언해놓은 2차원 배열 크기 선언
        arr = new int[N][M];

        // 2차원 배열 arr 값 초기화 stream API 안쓰는 게 시간 측면에서 유리
        for (int i = 0; i < N; i++) {
            arr[i] = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // 배열 회전 진행
        for (int i = 0; i < R; i++)
            arr = rotate(N, M);

        // 이후 2차원 배열 각 row에 대해 stream API 및 정규 표현식 활용하여 출력 버퍼에 담음
        Arrays.stream(arr)
                .forEach(data ->
                {
                    try {
                        bw.write(Arrays.toString(data)
                                .replaceAll("[\\[\\],]","")+"\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        bw.flush();

    }

    // 배열 회전 함수
    // arr에 있는 값을 내부 이차원 배열에 할당한 뒤 회전한 후 반환 -> arr에 할당하는 형식으로 구현
    public static int[][] rotate(int row, int col) {
        // 사각형의 개수 (static 하게 놓아도 괜찮을 것 같음)
        int sqrNum = Math.min(row, col) / 2;
        // 회전 값을 담을 2차원 배열 선언
        int[][] tmpArr = new int[row][col];
        for (int depth = 0; depth < sqrNum; depth++) {
            // 각 사각형에 대한 행, 컬럼 최대값 연산
            int rowMax = row - depth - 1;
            int colMax = col - depth - 1;

            //   (depth, depth) 기준 왼쪽으로 미는 연산
            for (int colIdx = colMax - 1; depth <= colIdx; colIdx--) {
                tmpArr[depth][colIdx] = arr[depth][colIdx + 1];
            }
            //   (depth, depth) 기준 아래로 미는 연산
            for (int rowIdx = rowMax; depth < rowIdx; rowIdx--) {
                tmpArr[rowIdx][depth] = arr[rowIdx - 1][depth];
            }
            //   (rowMax, depth) 기준 오른쪽으로 미는 연산
            for (int colIdx = colMax; depth < colIdx; colIdx--) {
                tmpArr[rowMax][colIdx] = arr[rowMax][colIdx - 1];
            }
            //   (rowMax, colMax) 기준 위로 미는 연산
            for (int rowIdx = depth; rowIdx < rowMax; rowIdx++){
                tmpArr[rowIdx][colMax] = arr[rowIdx+1][colMax];
            }
        }
        return tmpArr;
    }
}
