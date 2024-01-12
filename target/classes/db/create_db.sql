--Перед выполнением скрипта создаём БД в postgres
--CREATE DATABASE archive;

DO $$
BEGIN
    --если БД создана
    IF EXISTS (SELECT 1 FROM pg_database WHERE datname = 'archive') THEN
        BEGIN
            --если таблица не существует
            IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'account') THEN
            --создаю таблицу с информацией о пользователях
                CREATE TABLE account (
                    id serial4 NOT NULL,
                    fio varchar(500) NULL,
                    login varchar(100) NULL,
                    email varchar(150) NULL,
                    pwd varchar(500) NULL
                );                
                RAISE NOTICE 'Создана таблица account';
                --если в таблице нет УЗ администратора
                IF NOT EXISTS (SELECT 1 FROM account WHERE fio='Администратор' AND login='admin') THEN
                    --создаём её
                    INSERT INTO account (fio,email,login,pwd) VALUES('Администратор','admin@sakhalin.gov.ru','admin','$2a$12$FW43mukkuN13bs0DfTgq9OCno7vqVjyoeTNXCSy8Qp80vqf/M7.Hm');
                    RAISE NOTICE 'Добавлена учётная запись администратора';
                    RAISE NOTICE 'Учётная запись для входа - admin\admin';
                ELSE
                    RAISE NOTICE 'Учётная запись администратора добавлена в таблицу users ранее';
                END IF;
            END IF;
        END;
    --если база данных не создана
    ELSE
        RAISE NOTICE 'Не создана БД с именем archive';
    END IF;
END $$;
