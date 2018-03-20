package com.fzm.cgb;

public class Te {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Te.oneByOneHash("dengqun"));
		System.out.println(Te.oneByOneHash("dengqun"));
		System.out.println(Te.oneByOneHash("12345"));
		System.out.println(Te.oneByOneHash("123456"));
		System.out.println(Te.oneByOneHash("1234560"));
		System.out.println(Te.oneByOneHash("1234561"));
		System.out.println(Te.oneByOneHash("1234561"));
		System.out.println(Te.oneByOneHash("15394287853"));
	}
    public static int oneByOneHash(String key)
    {
        int   hash, i;
        for (hash=0, i=0; i<key.length(); ++i)
        {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        //   return (hash & M_MASK);
        return hash;
    }
}
