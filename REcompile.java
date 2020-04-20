class REcompile
{
    int j=0;
    char[] p;
    char[] sp = new char[]{'*','?','|','(',')','\\','+'};
    boolean flag = true;
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
        p = i.toCharArray();
        expression();
        if(flag)
        System.out.println("Valid Regex");
        else
        System.out.println("Invalid Regex");
    }
    public void expression()
    {
        term();
        if(IsVocab(p[j]) || p[j]=='(')
            expression();
    }
    public void term()
    {
        if(p[j]=='\\')
        {
            j++;factor();
        }
        else
        {
        factor();
        }
        if(p[j]=='*')
        {j++;return;}
        if(p[j]=='+')
        {
            j++;
            term();
            
        }
        
    }
    public void factor()
    {
        if( IsVocab(p[j]))
            j++;
        else if(p[j]=='\0')
        return;
        else if(p[j]=='(')
        {
            j++;
            expression();
            if(p[j]==')')
                j++;
            else error();
        }
        else
        error();
    }
    public void error()
    {
        flag = false;
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
    
}