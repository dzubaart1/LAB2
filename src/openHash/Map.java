package openHash;

public class Map
{
    public final int MAX_CLASS_COUNT = 5; // Максимальное кол-во классов

    private Item[] _array; // Массив

    // Инициализирующий конструктор
    public Map()
    {
        _array = new Item[MAX_CLASS_COUNT];
    }

    // Находится ли в множестве name
    public boolean Member(char[] name)
    {
        // Получаем хэш
        int hash = HashFunc(name);

        // Если по этому хэшу ничего нет, то false
        if(_array[hash] == null)
        {
            return false;
        }

        // Если в корне лежит необходимое значение, то true
        if(EqualsCharArrays(_array[hash].Name, name))
        {
            return true;
        }

        // Возвращаем предыдущее значение
        Item prevItem = GetPrevItem(hash, name);

        // Если нет его, то false, если есть, то true
        if(prevItem == null)
        {
            return false;
        }

        return prevItem.Next != null;
    }

    // Обнуление множества
    public void MakeNull()
    {
        for(int i = 0; i < MAX_CLASS_COUNT; i++)
        {
            _array[i] = null;
        }
    }

    // Вставка эл-та в множество
    public void Insert(char[] name)
    {
        // Вычисляем хэш
        int hash = HashFunc(name);

        // Если по этому хэшу пусто, то создаем эл-т, елы палы
        if(_array[hash] == null)
        {
            _array[hash] = new Item(name, null);
            return;
        }

        // Получаем предыдущий
        Item prevItem = GetPrevItem(hash, name);

        // Если корректный следующий эл-т, то return
        if(prevItem != null && EqualsCharArrays(prevItem.Next.Name, name))
        {
            return;
        }

        // Вставляем эл-т
        _array[hash] = new Item(name, _array[hash]);
    }

    // Удаляем эл-т из множества
    public void Delete(char[] name)
    {
        // Считаем хэш
        int hash = HashFunc(name);

        // Если по этому хэшу пусто, то создаем эл-т, елы палы
        if(_array[hash] == null)
        {
            return;
        }

        // Проверяем голову
        if(EqualsCharArrays(_array[hash].Name, name))
        {
            _array[hash] = _array[hash].Next;
            return;
        }

        // Получаем предыдущий
        Item prevItem = GetPrevItem(hash, name);

        // Если предыдущий null, то return
        if(prevItem == null)
        {
            return;
        }

        // Проверяем след эл-т
        if(EqualsCharArrays(prevItem.Next.Name, name))
        {
            prevItem.Next = prevItem.Next.Next;
        }
    }

    // Хэширующая функция
    private int HashFunc(char[] string)
    {
        int sum = 0;
        for(char symbol : string)
        {
            sum += symbol;
        }

        return sum % MAX_CLASS_COUNT;
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

    // Ищем эл-та по массиву a и возвращаем предыдущий
    private Item GetPrevItem(int hash, char[] a)
    {
        Item prevItem = null;
        Item currentItem = _array[hash];

        while (currentItem != null)
        {
            if(EqualsCharArrays(currentItem.Name, a))
            {
                return prevItem;
            }

            prevItem = currentItem;
            currentItem = currentItem.Next;
        }

        return null;
    }
}
