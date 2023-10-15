package kr.ac.jbnu.se.tetris;

public enum Tetrominoes {
	NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape, OneBlockShape
}

//테트리스 블록(테트로미노)의 모양을 정의한 클래스
/*
NoShape
[ ][ ][ ][ ]
[ ][ ][ ][ ]


ZShape
[ ][ ][X][X]
[ ][X][X][ ]


SShape
[ ][X][X][ ]
[ ][ ][X][X]

LineShape
[ ][ ][ ][ ]
[X][X][X][X]


TShape
[ ][X][X][X]
[ ][ ][X][ ]

SquareShape
[ ][X][X][ ]
[ ][X][X][ ]

LShape
[ ][X][X][X]
[ ][X][ ][ ]

MirroredLShape (반전된 L 모양)
[ ][X][X][X]
[ ][ ][ ][X]

OneBlockShape
[ ][ ][ ][ ]
[ ][ ][X][ ]


 */