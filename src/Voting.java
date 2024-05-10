import closeHash.Map;

import java.util.Arrays;
import java.util.Scanner;

public class Voting {
    private Map _goodGuys;
    private Map _badGuys;

    private final char CHECK_STATUS_CHAR = '?';
    private final char STOP_INPUT_CHAR = 'E';
    private final char RIGHT_VOTE_CHAR = 'R';
    private final char FALSE_VOTE_CHAR = 'F';

    public Voting()
    {
        _goodGuys = new Map();
        _badGuys = new Map();
    }

    public void StartVoting()
    {
        Scanner scanner = new Scanner(System.in);

        String nextLine = scanner.nextLine();
        char[] name;

        while (nextLine.charAt(0) != STOP_INPUT_CHAR)
        {
            name = nextLine.split(" ")[1].toCharArray();
            switch (nextLine.charAt(0))
            {
                case RIGHT_VOTE_CHAR:
                    _badGuys.Delete(name);
                    _goodGuys.Insert(name);
                    break;
                case FALSE_VOTE_CHAR:
                    _goodGuys.Delete(name);
                    _badGuys.Insert(name);
                    break;
                case CHECK_STATUS_CHAR:
                    CheckStatus(name);
                    break;
                default:
                    System.out.println("WRONG INPUT!!!");
                    break;
            }

            nextLine = scanner.nextLine();
        }
    }

    private void CheckStatus(char[] name)
    {
        if(_goodGuys.Member(name))
        {
            System.out.println(Arrays.toString(name) + " состоит в списке GoodGuys!");
        }
        else if(_badGuys.Member(name))
        {
            System.out.println(Arrays.toString(name) + " состоит в списке BadGuys!");
        }
        else
        {
            System.out.println(Arrays.toString(name) + " нет ни в одном списке!");
        }
    }
}

