package closeHash;

public class Map
{
    public final int MAX_CLASS_COUNT = 20; // Максимальное кол-во классов
    public final int MAX_NAME_LENGTH = 10; // Максимальная длина имени

    private char[][] _array;

    // Инициализирующий массив
    public Map()
    {
        _array = new char[MAX_CLASS_COUNT][MAX_NAME_LENGTH];
    }

    // Обнуление словаря
    public void MakeNull()
    {
        for(int i = 0; i < MAX_CLASS_COUNT; i++)
        {
            _array[i] = null;
        }
    }

    // Проверяем находится ли name в словаре
    public boolean Member(char[] name)
    {
        int num = 0;

        // Получаем хэш
        int hash = GetNextHash(HashFunc(name), num);

        // Запоминаем стартовый хэш
        int startHash = hash;


        // Пока мы не найлем не тронутую ячейку
        while (!IsEmptyArray(_array[hash]))
        {
            // Если совпали массивы, то true
            if(EqualsCharArrays(_array[hash], name))
            {
                return true;
            }

            // Получаем следующий хэш
            hash = GetNextHash(hash, num++);

            // Если вернулись в стартовый хэш
            if (hash == startHash)
            {
                return false;
            }
        }

        return false;
    }


    // Вставка эл-та в словарь
    public void Insert(char[] name)
    {
        int num = 0;

        // Получаем хэш
        int hash = GetNextHash(HashFunc(name), num);

        // Запоминаем стартовый хэш
        int startHash = hash;

        // Запоминаем очищенную ячейку
        int freeSpace = -1;


        // Пока мы не найлем не тронутую ячейку
        while (!IsEmptyArray(_array[hash]))
        {
            // Если совпали массивы, то return
            if(EqualsCharArrays(_array[hash], name))
            {
                return;
            }

            // Запоминаем удаленный эл-т
            if(IsItDeletedItem(_array[hash]) && freeSpace == -1)
            {
                freeSpace = hash;
            }

            // Получаем следующий хэш
            hash = GetNextHash(hash, num++);

            // Если вернулись в стартовый хэш, то break
            if (hash == startHash)
            {
                break;
            }
        }

        // Если мы дошли до нетронутого значения
        if(IsEmptyArray(_array[hash]))
        {
            _array[hash] = name;
            return;
        }

        // Если дошли до конца и нашли удаленный эл-т
        if (freeSpace != -1)
        {
            _array[freeSpace] = name;
        }
    }

    // Удаление эл-та из словаря
    public void Delete(char[] name)
    {
        int num = 0;

        // Получаем хэш
        int hash = GetNextHash(HashFunc(name), num);

        // Запоминаем стартовый хэш
        int startHash = hash;

        // Пока мы не найлем не тронутую ячейку
        while (!IsEmptyArray(_array[hash]))
        {
            // Если совпали массивы, то удаляем
            if(EqualsCharArrays(_array[hash], name))
            {
                _array[hash][0] = '\0';
                return;
            }

            hash = GetNextHash(hash, num++);

            if (hash == startHash)
            {
                return;
            }
        }
    }

    // Проверка на совпадение символьных массивов
    private boolean EqualsCharArrays(char[] a, char[] b)
    {
        if(a.length != b.length)
        {
            return false;
        }

        for(int i = 0; i < a.length; i++)
        {
            if(a[i] != b[i])
            {
                return false;
            }
        }

        return true;
    }

    // Хэширующая функция
    private int HashFunc(char[] string)
    {
        int sum = 0;
        for(char symbol : string)
        {
            sum += symbol;
        }

        return sum;
    }

    // Функция возвращает следующий хэш
    private int GetNextHash(int hash, int num)
    {
        return (hash + num) % MAX_CLASS_COUNT;
    }

    // Удаленный ли этот эл-т
    private boolean IsItDeletedItem(char[] value)
    {
        return value[0] == '\0';
    }

    // Пустой ли массив
    private boolean IsEmptyArray(char[] value)
    {
        for (int i = 0; i < value.length; i++)
        {
            if(value[i] != 0)
            {
                return false;
            }
        }

        return true;
    }
}
