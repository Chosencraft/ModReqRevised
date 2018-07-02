package com.chosencraft.purefocus;

public class Chat
{
	
	private static final int FONT_SIZES[] = {
	                                      1, 9, 9, 8, 8, 8, 8, 7, 9, 8, 9, 9, 8, 9, 9, 9, 8, 8, 8, 8, 9, 9,
	                                      8, 9, 8, 8, 8, 8, 8, 9, 9, 9, 4, 2, 5, 6, 6, 6, 6, 3, 5, 5, 5, 6,
	                                      2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 5, 6, 5, 6, 7, 6,
	                                      6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
	                                      6, 6, 6, 4, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6,
	                                      6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 6, 6, 6, 6, 6,
	                                      6, 6, 6, 6, 6, 6, 6, 4, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
	                                      6, 6, 6, 6, 4, 6, 6, 3, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 2, 6, 6,
	                                      8, 9, 9, 6, 6, 6, 8, 8, 6, 8, 8, 8, 8, 8, 6, 6, 9, 9, 9, 9, 9, 9,
	                                      9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 6, 9, 9,
	                                      9, 5, 9, 9, 8, 7, 7, 8, 7, 8, 8, 8, 7, 8, 8, 7, 9, 9, 6, 7, 7, 7,
	                                      7, 7, 9, 6, 7, 8, 7, 6, 6, 9, 7, 6, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 6, 6, 3, 6, 6, 6, 4, 6, 2,
	                                      2, 6, 4, 6, 5, 5, 4, 6, 5, 3, 6, 6, 6, 6, 6, 4, 6, 1, 1, 1, 1, 1,
	                                      1, 6, 6, 3, 6, 6, 6, 4, 6, 2, 2, 6, 4, 6, 5, 5, 4, 6, 5, 3, 6, 6,
	                                      6, 6, 6, 4, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	                                      1, 1, 1, 1, 1, 1
	                                      };
	
	public static int        MAX_WIDTH    = 318;
	
	public static String center(final String s)
	{
		
		final int strSize = Chat.stringWidth(s);
		final int fillerSize = Chat.stringWidth(" ");
		
		final StringBuilder string = new StringBuilder();
		
		final int toFill = Chat.MAX_WIDTH / 2 - strSize / 2;
		for(int i = 0; i * fillerSize < toFill; i++)
		{
			string.append(" ");
		}
		string.append(s);
		
		return string.toString();
	}
	
	public static String trim(String input, String trim)
	{
		int width = stringWidth(input);
		int width2 = stringWidth(trim);
		if(width + width2 <= MAX_WIDTH)
		{
			return input + trim;
		}
		else
		{
			StringBuilder builder = new StringBuilder();
			builder.append(input);
			char[] chars = trim.toCharArray();
			for(char ch : chars)
			{
				if((width += charWidth(ch)) <= MAX_WIDTH)
				{
					builder.append(ch);
				}
				else
				{
					break;
				}
			}
			return builder.toString().trim();
		}
	}
	
	public static int charWidth(final char s)
	{
		if(s == 32)
		{
			return 4;
		}
		else if(s == 124)
			return 2;
		else if(s == 34678 || s == 8776)
			return 3;
		return Chat.FONT_SIZES[s + 32];
	}
	
	public static String fill(final String filler)
	{
		final int size = Chat.stringWidth(filler);
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i * size < Chat.MAX_WIDTH; i++)
		{
			builder.append(filler);
		}
		return builder.toString();
	}
	
	public static String format(final String in)
	{
		if(in == null)
		{
			return "";
		}
		return in.replaceAll("&([0-9a-f|k|l|m|n])", "\247$1");
	}
	
	public static void main(final String[] args)
	{
		System.out.println(Chat.fill("≈"));
	}
	
	public static int stringWidth(String s)
	{
		if(s == null)
		{
			return 0;
		}
		else
		{
			s = s.replaceAll("(&|\247)([0-9a-f|k|l|m|n|])", "");
			int size = 0;
			for(int index = 0; index < s.length(); ++index)
			{
				final char ch = s.charAt(index);
				size += Chat.charWidth(ch);
			}
			return size;
		}
	}
	
}
