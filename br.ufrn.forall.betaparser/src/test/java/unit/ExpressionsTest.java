package unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import parser.Machine;

public class ExpressionsTest {

	@Test
	public void shouldParseAllKindsOfExpressions() {
		Machine machine = getMachineInstance("src/test/resources/machines/others/ExpressionsTest.mch");
		
		Set<String> expectedPreconditions = new HashSet<String>();
		
		expectedPreconditions.add("(aa + bb) = cc"); // add and identifier
		expectedPreconditions.add("dd = TRUE"); // boolean true
		expectedPreconditions.add("ee = FALSE"); // boolean false    	      
		expectedPreconditions.add("dd : BOOL"); // boolean set
		expectedPreconditions.add("ee : BOOL"); // boolean set
		expectedPreconditions.add("kk : STRING"); // string set
		expectedPreconditions.add("ff <: NAT"); // nat set
		expectedPreconditions.add("card(ff) = 2"); // card and integer
		expectedPreconditions.add("dom(gg) = {0, 1}"); // domain and set extension
		expectedPreconditions.add("hh <: NAT1"); // nat1 set
		expectedPreconditions.add("hh = {}"); // empty set
		expectedPreconditions.add("gg(0) = TRUE"); // function expression
		expectedPreconditions.add("ran(gg) = {TRUE, FALSE}"); // range
		expectedPreconditions.add("ff /\\ hh = {}"); // intersection
		expectedPreconditions.add("ff \\/ hh /= {}"); // union
		expectedPreconditions.add("ii : 1..10"); // interval
		expectedPreconditions.add("jj : INT"); // int set
		expectedPreconditions.add("jj < MAXINT"); // maxint
		expectedPreconditions.add("bb = (cc - aa)"); // MinusOrSetSubtract
		expectedPreconditions.add("jj > (aa * bb)"); // MultOrCart
		expectedPreconditions.add("nn : POW(PEOPLE)"); // PowSubset		
		expectedPreconditions.add("ll : (hh +-> BOOL)"); // PartialFunction
		expectedPreconditions.add("gg : (ff --> BOOL)"); // TotalFunction
		expectedPreconditions.add("oo : (ff -->> BOOL)"); // TotalSurjection
		expectedPreconditions.add("mm : (hh >+> BOOL)"); // PartialInjection
		expectedPreconditions.add("pp : (ff >-> BOOL)"); // TotalInjection
		expectedPreconditions.add("qq : (ff >+>> BOOL)"); // PartialBijection
		expectedPreconditions.add("rr : (ff >->> BOOL)"); // TotalBijection
		
		expectedPreconditions.add("ss : (NAT <-> NAT)"); // Relations
		expectedPreconditions.add("ss1 : ({1} <<| ss)"); // DomainSubtraction
		expectedPreconditions.add("ss2 : (ss |>> {1})"); // RangeSubtraction
		expectedPreconditions.add("ss3 : ({1} <| ss)"); // DomainRestriction
		expectedPreconditions.add("ss4 : (ss |> {1})"); // RangeRestriction
		expectedPreconditions.add("ss5 : (ss - {1 |-> 2})"); // Couple (Maplet)
		expectedPreconditions.add("ss6 : (rr~)"); // Reverse
		expectedPreconditions.add("ss7 : (ss <+ {5 |-> 5})"); // Overwrite
		
		expectedPreconditions.add("tt : -1..3"); // UnaryMinus
		expectedPreconditions.add("uu : (NAT --> iseq(nn))"); // Injective sequence
		expectedPreconditions.add("vv = []"); // Empty sequence
		expectedPreconditions.add("xx = first(uu)"); // first expression
		expectedPreconditions.add("zz : union(uu)"); // General Union
		
		expectedPreconditions.add("yy = prj1(NAT, PEOPLE)"); // First Projection
		expectedPreconditions.add("ww = prj2(NAT, PEOPLE)"); // Second Projection
		expectedPreconditions.add("aa1 = (NAT; PEOPLE)"); // Composition
		expectedPreconditions.add("aa2 = {vv | vv : NAT}"); // Set Comprehension
		expectedPreconditions.add("aa3 : (NAT --> seq(nn))"); // Sequence
		expectedPreconditions.add("aa4 = last(uu)"); // Sequence Last
		expectedPreconditions.add("aa5 = size(vv)"); // Sequence Size
		expectedPreconditions.add("aa6 = front(vv)"); // Sequence Front
		expectedPreconditions.add("aa7 = tail(vv)"); // Sequence Tail
		expectedPreconditions.add("aa8 = rev(vv)"); // Sequence Rev
		
		expectedPreconditions.add("aa9 = (4/2)"); // Div Expression
		expectedPreconditions.add("aa10 = (10 mod 2)"); // Div Expression
		
		// TODO: ImageExpression
		// TODO: FinSubsetExpression
		// TODO: LambdaExpression		
		// TODO: sequence expressions
		Set<String> actualPreconditions = new HashSet<String>();
		actualPreconditions.addAll(machine.getOperation(0).getPreconditionClausesAsList());
		
		assertEquals(expectedPreconditions, actualPreconditions);
	}
	
	
	
	private Machine getMachineInstance(String path) {
		Machine machine = new Machine(new File(path));
		return machine;
	}
	
}
