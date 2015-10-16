MACHINE
   Division
PROPERTIES	!(aa,bb,vv) . (aa:NAT & bb:NAT1 & vv:NAT => (aa*bb+vv) mod bb = vv mod bb) &
	   !(aa,bb)   . (aa:NAT & bb:NAT1 => (aa<bb => aa = aa mod bb))

OPERATIONS
   qq, rr <-- div(aa, bb) = 
	PRE	aa : NAT & bb : NAT1
	THEN	qq, rr := aa/bb, aa mod bb
	END	
END