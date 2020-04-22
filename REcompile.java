import java.util.Arrays;

class REcompile
{
    int j=0;
    int state=1;
    char[] p;
    char[] sp = new char[]{'*','?','|','(',')','\\','|'};
    char[] ch;
    int[] nxt1;
    int[] nxt2;
    public static void main(String args[])
    {
        
        if(args.length!=1)
        System.err.println("Invalid Input:\nExpected <regex>");
        char c = '\0';
        args[0]+=c;
        REcompile rc = new REcompile();
        rc.process(args[0]);
        

    }
    public void process(String i)
    {
        long count1 = i.chars().filter(x -> x == '(').count();
        long count2 = i.chars().filter(x -> x == ')').count();
        if(count1!=count2)
            error();
        p = i.toCharArray();
        ch = new char[p.length];
        nxt1 = new int[p.length];
        nxt2 = new int[p.length];
        Arrays.fill(nxt1, -1);
        Arrays.fill(nxt2, -1);
        int r = expression();
        setState(0, ' ', r, r);
 
        System.out.println("Valid Regex");
        displayFSM();
     
      
    }
    public int expression()
    {
        int r=term();
        if(IsVocab(p[j]) || p[j]=='(')
            expression();
        return r;
    }

    public int term()
    {
       int r,t1,t2,f;
       f=state-1; 
       r=factor();
       t1=r;
        
        
        if(p[j]=='*')
        {
           
            setState(state, ' ', state+1, t1);
            j++;state++;
        }
        if(p[j]=='?')
        {
             setState(state-1, ' ', state, state+1);
             setState(state, ' ', state+1, state+1);
            j++;state++;
        }
        if(p[j]=='|')
        {
            if(nxt1[f]==nxt2[f])
	        nxt2[f]=state;
            nxt1[f]=state;
            f=state-1;
            j++;r=state;state++; 
            t2=term();
            setState(r,' ',t1,t2);
            if(nxt1[f]==nxt2[f])
            nxt2[f]=state;
            nxt1[f]=state;
            
        }
        return r;
    }
    
    public int factor()
    {
      int r=state;
        if(p[j]=='\\')
        {
            j++;
            setState(state, p[j], state+1, state+1);
            j++;r=state;state++;
        } 
        if( IsVocab(p[j]))
            {
                
                setState(state, p[j], state+1, state+1);
                j++;r=state;state++;
                
            }
        else if(p[j]=='\0')
        return -1;
        
        else if(p[j]=='(')
        {
            j++;
           r = expression();
            if(p[j]==')')
                {j++; }

            else error();
        }
        
        else
            error();
        return(r);
        
    }
    public void error()
    {
        System.out.println("Invalid Regex");
        System.exit(0);       
    }
    public boolean IsVocab(char i)
    { 
        
        if((i>=32 && i<=126))
        {
            for (char c : sp) {
                if(c==i)
                    return false;
                
            }
            return true;
        }
           
        return false;
    }
    public void setState(int s,char c,int n1,int n2)
    {
        ch[s]=c;
        nxt1[s]=n1;
        nxt2[s]=n2;
    }


    public void displayFSM()
    {
      for (char c : ch) {
          System.out.print(c+" ");
      }
      System.out.println("\n");
      for (int n : nxt1) {
        System.out.print(n+" ");
      }
      System.out.println("\n");
      for (int n : nxt2) {
        System.out.print(n+" ");
      }
    }
    
}