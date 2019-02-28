package spell;

/**
 * Created by hale38 on 2/2/18.
 */

public class trie implements ITrie {

    node root = new node();
    int nodeCount;
    int wordCount;

     public trie (){
        nodeCount=0;
        wordCount=0;
        root = new node();
         root.frequency++;

    }



    @Override
    public void add(String word) {
        StringBuilder builder = new StringBuilder(word);
        root.addWordRecurse(builder);
    }

    @Override
    public INode find(String word) {

        if (word.length()==0)return null;
        return root.findRecurse(new StringBuilder(word.toLowerCase()));
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString()
    {
        StringBuilder list = new StringBuilder();
        StringBuilder word = new StringBuilder();
        root.toStringRecurse(list, word);
       // System.out.println(list.toString());
        return list.toString();
    };


    @Override
    public int hashCode()
    {
        return wordCount + nodeCount + 7;
    };


    @Override
    public boolean equals(Object o)
    {
        if ( o == null) return false;
        if (o instanceof trie) {

            trie compare = (trie) o;

            if (hashCode() != o.hashCode()) return false;

           return equalsRecurse(root, compare.root);
        }

        return false;
    };

    public boolean equalsRecurse(node first, node second){
        if (first.frequency!=second.frequency)return false;
        for(int i=0; i<26; i++) {
            if (first.children[i] != null && second.children[i] != null) {

                if(!equalsRecurse(first.children[i], second.children[i]))return false;
            }

            else if (!(first.children[i] == null && second.children[i] == null))
            {
                return false;
            }
        }
        return true;
    }




    class node implements INode{
        int frequency;
        node [] children;

        public node()
        {
            children = new node[26];
            nodeCount++;
        }

        @Override
        public int getValue() {
            return frequency;
        }

        private void addWordRecurse(StringBuilder word)
        {
            if(word.length()==0)
            {
                if (frequency==0) {
                    wordCount++;
                }
                frequency++;
            }
            else{
                char c = word.charAt(0);
                word.deleteCharAt(0);
                if (children[c-'a']==null)
                {
                    children[c-'a'] = new node();
                    children[c-'a'].addWordRecurse(word);
                }
                else
                {
                    children[c-'a'].addWordRecurse(word);
                }
            }
        }

        private void toStringRecurse(StringBuilder list, StringBuilder word)
        {
            if (frequency >= 1)
            {
               // System.out.println(word);
                list.append(word);
                list.append('\n');
            }

            for (int i =0; i < 26; i++){
                if (children[i]!=null)
                {
                    //System.out.println((char)('a'+ i ));
                    word.append((char)('a'+ i ));
                    children[i].toStringRecurse(list,word);
                    word.deleteCharAt(word.length()-1);
                }
            }
        }

        private INode findRecurse(StringBuilder word)
        {
            if(word.length()==0)
            {
                if (frequency >=1)
                {
                    return this;
                }

                else return null;
            }

            char c = word.charAt(0);

            try {
                if (children[c - 'a'] == null) return null;
            }
            catch (java.lang.ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Here");
            }
            word.deleteCharAt(0);
            return children[c-'a'].findRecurse(word);

        }




    }




}
