
#include <cppunit/extensions/HelperMacros.h>
#include <cppunit/extensions/TestFactoryRegistry.h>
#include <cppunit/TestRunner.h>

/** 
* Machine: Fifo
* Operation: output
*
* Partition Strategy: Equivalence Classes
* Combination Strategy: All-Combinations
* Oracle Strategy: Check the state invariants, state variables and return values
*/
class FifoOutputTest : public CppUnit::TestFixture
{
	CPPUNIT_TEST_SUITE( FifoOutputTest );
	
	CPPUNIT_TEST( testCase1 );
	
	CPPUNIT_TEST( testCase2 );
	
	CPPUNIT_TEST_SUITE_END();
	
	private:
		Fifo *fifo;
	
	public:
		void setUp()
		{
			fifo = new Fifo;
		}
		
		void tearDown()
		{
			delete fifo;
		}
		
		void checkInvariant()
		{
			// implement something to check the state invariants
		}
		
		
		/**
		* Test Case 1
		* Formula: not(size(contents) > 0) & size(contents) <= cap & contents : seq(INT)
		*/
		void testCase1()
		{
			// State   
			seq(INT) contents = [];
			fifo->setContents(contents);
			 
			   
			
			CPPUNIT_ASSERT( fifo->output() == /* Add expected value here */ );
			
			
			
			seq(INT) contentsExpected;	// Add expected value here.
			CPPUNIT_ASSERT( fifo->getContents() == contentsExpected );
			   	
			
			checkInvariant(); // calling check invariant
			
		}
		
		/**
		* Test Case 2
		* Formula: size(contents) > 0 & size(contents) <= cap & contents : seq(INT)
		*/
		void testCase2()
		{
			// State   
			seq(INT) contents = [-20];
			fifo->setContents(contents);
			 
			   
			
			CPPUNIT_ASSERT( fifo->output() == /* Add expected value here */ );
			
			
			
			seq(INT) contentsExpected;	// Add expected value here.
			CPPUNIT_ASSERT( fifo->getContents() == contentsExpected );
			   	
			
			checkInvariant(); // calling check invariant
			
		}
		
} 


