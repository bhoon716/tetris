package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import java.util.Random;

public class OneBlock extends Board {
    private OneBlockShape curOneBlock; // 현재 원 블록을 나타내는 변수
    private int curOneBlockX = 0; // 현재 원 블록의 X 좌표
    private int curOneBlockY = 0; // 현재 원 블록의 Y 좌표
	private boolean isOneBlockFallingFinished = false;

    public OneBlock(Tetris tetris) {
        super(tetris, "원 블럭 모드");
        curOneBlock = new OneBlockShape();        
    }

	@Override
	public void actionPerformed(ActionEvent e) { //타이머가 400ms마다 호출하는 메소드
		if (isFallingFinished) { //블록이 떨어지는 것이 끝났다면
			isFallingFinished = false; //끝났음을 나타내는 변수를 false로 설정
			newPiece(); //새로운 블록을 생성

		} else { //떨어지는 것이 끝나지 않았다면
			oneLineDown(); //블록을 한 칸 아래로 이동
		}
        
        if (isOneBlockFallingFinished){
            isOneBlockFallingFinished = false;
            newOneBlock();

        } else{
            oneBlockDown();
        }
	}

	private void newOneBlock() { //새로운 블록을 생성하는 메소드
        Random r = new Random(); //랜덤 객체 생성
		curOneBlockX = r.nextInt(BoardWidth); //0부터 BoardWidth-1까지의 랜덤한 숫자를 생성
		curOneBlockY = 0; //새로운 블록의 y좌표

		if (!tryOneBlockMove(curOneBlock, curOneBlockX, curOneBlockY)) { //새로운 위치로 블록을 이동할 수 없다면
		}
	}

    public boolean tryOneBlockMove(OneBlockShape newOneBlock, int newX, int newY) { //블록을 새로운 위치로 이동할 수 있는지 확인하는 메소드
        int x = newX + newOneBlock.getOneBlockX(); //새로운 x좌표
        int y = newY - newOneBlock.getOneBlockY(); //새로운 y좌표
        if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight){ //새로운 블록이 게임 보드의 범위를 벗어난다면
			isOneBlockFallingFinished = true;
			return false; //false 반환
		}
		if (shapeAt(x, y) != Tetrominoes.NoShape){ //새로운 블록이 게임 보드의 다른 블록과 겹친다면 = 새로운 x, y에 블록이 존재한다면
			isOneBlockFallingFinished = true;
			return false; //false 반환
		}
		curOneBlock = newOneBlock; //새로운 블록을 현재 블록으로 설정
		curOneBlockX = newX; //현재 블록의 x좌표를 newX로 설정
		curOneBlockY = newY; //현재 블록의 y좌표를 newY로 설정
		repaint(); //게임 보드를 다시 그림
		return true; //true 반환
    }

    public void oneBlockDown() { //블록을 한 칸 아래로 이동하는 메소드
        if(!tryOneBlockMove(curOneBlock, curOneBlockX, curOneBlockY - 1)){
            int x = curOneBlockX + curOneBlock.getOneBlockX(); //현재 블록의 x좌표
            int y = curOneBlockY - curOneBlock.getOneBlockY(); //현재 블록의 y좌표
            board[(y * BoardWidth) + x] = curOneBlock.getOneBlockShape(); //게임 보드의 (x, y) 위치에 현재 블록의 모양을 저장
    
            // checkOneBlockFallingFinished(); //블록을 내리고 가득 찬 줄이 있으면 제거
    
            if (!isOneBlockFallingFinished) //블록이 떨어지는 것이 끝나지 않았다면
                newOneBlock(); //새로운 블록을 생성
        }
    }

    public void checkOneBlockFallingFinished(){
		if(!tryOneBlockMove(curOneBlock, curOneBlockX, curOneBlockY - 1)){
			isOneBlockFallingFinished = true;
		}
	}
}
