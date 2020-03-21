// Autor:      Edwin J. Estrella Ayala
// 		       A00549508
// Curso:      COMP 3800 - 78112
// Profesor:   J. Navarro
// Asignacion: Diseño de Analizador de Sintaxis


package sintaxAnalyzer;

public class Expression {
	String expression;
	
	public Expression() {}
	
	public Expression(String e) {
		expression = e;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public void setExpression(String e) {
		expression = e;
	}
	
	static boolean isDigit(char c) {
		boolean condicion = false;
		if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
			condicion = true;
		}
		return condicion;
		
	}
	
	static boolean isVowel(char c) {
		boolean condicion = false;
		if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			condicion = true;
		}
		return condicion;
	}
	
	static boolean isOperator(char c) {
		boolean condicion = false;
		if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^') {
			condicion = true;
		}
		return condicion;
	}
	
	static boolean isOperatorStr(String str) {
		boolean condicion = false;
		String nextStr;
		if(str.length() == 1) {
			condicion = isOperator(str.charAt(0));
		}
		else {
			nextStr = str.substring(1, str.length());
			if(isOperator(str.charAt(0)) && isOperatorStr(nextStr)) {
				condicion = true;
			}
		}
		return condicion;
	}
	
	static boolean isVocalStr(String str) {
		boolean condicion = false;
		String nextStr;
		if(str.length() == 1 && isVowel(str.charAt(0))) {
			condicion = true;
		}
		else {
			nextStr = str.substring(1, str.length());
			if(isVowel(str.charAt(0)) && isVocalStr(nextStr)) {
				condicion = true;
			}
		}
		return condicion;
	}
	
	static boolean isNumber(String str) {
		boolean condicion = false;
		String nextStr;
		if(str.length() == 1) {
			condicion = isDigit(str.charAt(0));
		}
		else {
			nextStr = str.substring(1, str.length());
			if(isDigit(str.charAt(0)) && isNumber(nextStr)) {
				condicion = true;
			}
		}
		return condicion;
	}
	
	static boolean metodoSec1(String str) {
		boolean condicion = false;
		String nextStr;
		if(str.length() < 2) {
			condicion = false;
		}
		else {
			if(isDigit(str.charAt(0))) {
				nextStr = str.substring(1, str.length());
				if(isVocalStr(str.substring(1))) {
					condicion = true;
				}
				else {
					if(metodoSec1(nextStr) && isVowel(str.charAt(nextStr.length()))) {
						condicion = true;
					}
				}
			}
		}
		return condicion;
	}
	
	static boolean metodoSec1Rec(String str) {
		boolean condicion = false;
		String nextStr;
		if(metodoSec1(str)) {
			condicion = true;
		}
		else {
			nextStr = str.substring(1);
			if(isOperatorStr(str)) {
				condicion = true;
			}
			else {
				if(metodoSec1Rec(nextStr)) {
					if(!(isVowel(str.charAt(0)) && isDigit(nextStr.charAt(0)))) {
						condicion = true;
					}
				}
			}
		}
		return condicion;
	}
	
	static boolean isValidForSec1(String str) {
		boolean condicion = false;
		if(str.length() < 2 || !isDigit(str.charAt(0)) || isDigit(str.charAt(str.length() - 1))) {
			condicion = false;
		}
		else {
			if(metodoSec1(str) || metodoSec1Rec(str)) {
				condicion = true;
			}
		}
		return condicion;
	}
	
	static boolean sec2NextStr(String str) {
		int count = 0;
		if(isOperator(str.charAt(0))) {
			for(int i = 1; i < str.length(); i++) {
				if(isOperatorStr(str.substring(0, i))) {
					count++;
				}
				else {
					i = str.length();
				}
			}
		}
		return isOperatorStr(str.substring(0, count)) && isVocalStr(str.substring(count, str.length()));
	}
	
	static boolean sec2VocalStr(String str) {
		int count = 0;
		for(int i = 1; i < str.length(); i++) {
			if(isVocalStr(str.substring(0, i))) {
				count++;
			}
			else {
				i = str.length();
			}
		}
		return isVocalStr(str.substring(0, count)) && sec2NextStr(str.substring(count, str.length()));
	}
	
	static boolean isValidForSec2(String str) {
		int count = 0;
		boolean condicion = false;
		if (isVowel(str.charAt(0))) {
			if(isVocalStr(str)) {
				condicion = false;
			}
			condicion = sec2VocalStr(str);
		}
		else if(isDigit(str.charAt(0))) {
			for(int i = 1; i < str.length(); i++) {
				if(isNumber(str.substring(0, i))) {
					count++;
				}
				else {
					i = str.length();
				}
			}
			condicion = isNumber(str.substring(0, count)) && sec2VocalStr(str.substring(count, str.length()));
		}
		return condicion;
	}
	

	
	public static void main(String[] args) {
		String s1 = "1ae";
		String s2 = "12a";
		String s3 = "1a+";
		String s4 = "123uoiea+//*/";
		String s5 = "3";
		String s6 = "3+4";
		String s7 = "3a+3";
		String s8 = "o+";
		System.out.println("Casos para Sec1:");
		System.out.println("1: " + isValidForSec1(s1));
		System.out.println("2: " + isValidForSec1(s2));
		System.out.println("3: " + isValidForSec1(s3));
		System.out.println("4: " + isValidForSec1(s4));
		System.out.println("5: " + isValidForSec1(s5));
		System.out.println("6: " + isValidForSec1(s6));
		System.out.println("7: " + isValidForSec1(s7));
		System.out.println("8: " + isValidForSec1(s8));
		
		
		String l1 = "a*e";
		String l2 = "uiae//*+i";
		String l3 = "5ae^/iu";
		String l4 = "234ii--ea";
		String l5 = "aeiou";
		String l6 = "ae*";
		String l7 = "123";
		String l8 = "1+2";
		String l9 = "12ae";
		System.out.println("Casos para Sec2");
		System.out.println("1: " + isValidForSec2(l1));
		System.out.println("2: " + isValidForSec2(l2));
		System.out.println("3: " + isValidForSec2(l3));
		System.out.println("4: " + isValidForSec2(l4));
//		System.out.println("5: " + isValidForSec2(l5));
//		System.out.println("6: " + isValidForSec2(l6));
//		System.out.println("7: " + isValidForSec2(l7));
//		System.out.println("8: " + isValidForSec2(l8));
//		System.out.println("9: " + isValidForSec2(l9));
	}

}
