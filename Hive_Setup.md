**[HIVE** **설치와 환경설정 ]**

1.tools 디렉토리로 옮겨가서 다음 명령을 수행하고 HIVE 프로그램을 내려 받는다.

```li
wget  http://apache.tt.co.kr/hive/hive-2.3.5/apache-hive-2.3.5-bin.tar.gz
```


2.압축을 해제한 다음에 apache-hive-2.3.5 -bin 폴더를 홈 디렉토리에 apache-hive 폴더로 이동한다.

 

3.홈디렉토리의 .bashrc 를 열고 제일 아래에 다음을 추가한후  source 명령을 수행한다. 

```li
export HIVE_HOME=/root/apache-hive

export PATH=$HIVE_HOME/bin:$PATH
```



4.HIVE 의 conf 디렉토리로 이동한다. (cd  $HIVE_HOME/conf)            

​         hive-env.sh.template 을 hive-env.sh 으로 복사한다.

​         hive-default.xml.template  을 hive-site.xml 로 복사한다.  

```li
cd $HIVE_HOME/conf
cp hive-env.sh.template hive-env.sh
cp hive-default.xml.template hive-site.xml.
```



5.Hive-env.sh 에 하둡의 홈디렉토리를 제일 아래에 추가한다. 

```li
HADOOP_HOME=/root/hadoop-2.7.7
```



6.hive-site.xml 파일을 오픈하여 다음 내용대로 수정한다.

```li
cd $HIVE_HOME/conf
vi hive-site.xml
```

 **75****행**     **<value>/root/apache-hive/hivetmp</value>**

**80****행**     **<value>/root/apache-hive/${hive.session.id}_resources</value>**

**1685****행**   **<value>/root/apache-hive/hivetmp</value>**

 **3976****행**   **<value>/root/apache-hive/hivetmp/operation_logs</value>**

  **<!-- <name>hive.server2.enable.doAs</name> -->**

**4359****행**     **<value>false</value>  <---이거는 원격 접속 해제를 위해서 **

true를 false로 바꿔준다.

 

7.HADOOP 의 HDFS 에 HIVE 에서 사용될 디렉토리들을 생성하고 사용자 허가모드를  777 로 만든다.

```li
hdfs dfs  -mkdir  /tmp/hive

hdfs dfs  -chmod  777  /tmp/hive

hdfs dfs  -mkdir  -p /user/hive/warehouse

hdfs dfs  -chmod  777  /user/hive/warehouse
```



8.root 계정의 홈디렉토리에서 다음 명령을 수행해서 derby 데이터베이스를 포맷한다. 

```li
schematool -initSchema -dbType derby
```

9.HIVE 서버를 기동시킨다. - 항상 root 계정의 홈디렉토리에서 수행시킨다.

```li
hiveserver2
```



10.HIVE 클라이언트를 기동시킨다.

```li
[root@master ~]# beeline

Beeline version 1.2.1.spark2 by Apache Hive

beeline>!connect jdbc:hive2://192.168.111.120:10000/default

Connecting to jdbc:hive2://192.168.111.120:10000/default

Enter username for jdbc:hive2://192.168.111.120:10000/default: root

Enter password for jdbc:hive2://192.168.111.120:10000/default: password
```



default에 접속 성공 하면 다음과 같이 뜸.

```li
Connected to: Apache Hive (version 2.3.5)
Driver: Hive JDBC (version 2.3.5)
Transaction isolation: TRANSACTION_REPEATABLE_READ
0: jdbc:hive2://192.168.111.120:10000/default> 
```



default에서 다음 명령을 수행하여 현재 만들어져 있는 데이터베이스를  체크하고

학습용 db를 생성한다. 생성 후 !quit로 beeline을 종료후 다시 mydb로 하이브에 접속한다.

```
0: jdbc:hive2://localhost:10000/default> show databases;

0: jdbc:hive2://localhost:10000/default> create database mydb;

0: jdbc:hive2://localhost:10000/default> show databases;

0: jdbc:hive2://localhost:10000/default> !quit     <---------  beeline 종료 명령어
```



11.mydb 라는 데이터베이스로 하이브에 접속한다.

```li
beeline> !connect  jdbc:hive2://192.169.111.120:10000/mydb

Enter username for jdbc:hive2://localhost:10000/default: root

Enter password for jdbc:hive2://localhost:10000/default: ********

참고 : beeline -u  jdbc:hive2://192.168.111.120:10000/DB명  -n  계정  -p 암호
```

 

12.테이블 생성과 데이터 저장(모든 명령은 한 줄에 쭉 이어서 작성)

​    HIVE 로 접근하기 위한 HDFS 파일의 스펙에 따라 테이블 생성

-HiveSQL 쿼리문.

```sql
beeline>create table clicklog(clickdate string, pid string)row format delimited fields terminated by ' ' lines terminated by '\n' stored as textfile; 
```



-->생성된 테이블에 로컬 파일 시스템의 데이터 파일 로드

-->새로컬 파일 시스템의 파일을 HIVE 테이블의 데이터 파일로 저장하는 것이다. 

```sql
beeline>load data local inpath '/root/sampledata/product_click.log'  into table clicklog; 
```



-->이하 하둡파일시스템에 /user/  루트에 폴더 형식으로 저장이 되어 있는지 확인한다.

```sql
[root@master ~]# hdfs dfs -cat /user/hive/warehouse/mydb.db/clicklog/product_click.log
```



다음 SQL 명령들도 수행시켜본다. 

```li
select clickdate from clicklog;                                

select pid from clicklog;

select * from clicklog;           <--이런것들은 맵리듀스 필요없어서안함.                              
select * from clicklog order by pid;<--해당 명령 수행할땐 맵리듀스수행

                                      (Hive가 Job을 생성해서 맵리듀스에 전달해준다.)
                                      (hiveserver2 터미널에서 작업 확인 가능.)
			  
describe database mydb;                                       

describe clicklog;

select pid, count(pid) from clicklog group by pid;

select pid, count(pid) from clicklog group by pid limit 5; (맨위 5개를 조회함.)

select pid,count(pid)clickcount from clicklog (아래 쿼리문이랑 이어서)
group by pid order by clickcount;    <----(clickcount 작은 순서부터 큰순서로 조회)
```



13.파티션 기능을 활용한 테이블 활용 ( 다 이어서 하기)

```sql
create table subway (line string, time string, ride int, takeoff int)partitioned by (linep string)row format delimited fields terminated by ','  lines terminated by '\n' stored as textfile ;
```



↑↑↑↑위의 코드를 해석하면 다음과 같다.

```sql
create table subway (line string, time string, ride int, takeoff int)partitioned by (linep string)<---ß---라인별로 파티션화해서 나누겠다.

row format delimited fields terminated by ','<---ß---콤마로 구분하고,

lines terminated by '\n' <---ß---행처리는 \n 으로 하겠다!!

stored as textfile ;
```

   

-Home의 sampledata에 있는 subway_line1~4.csv파일을 

-Hive에서 partition 구분을 한 테이블에 하나씩 가지고 와서 파일 그대로 저장한다.

-핵심은 (linep = 'line_1') , (linep = 'line_2')~~~이렇게 지정해줘서 각각의 파일들을 받는다.

```sql
load data local inpath '/home/hadoop/sampledata/subway_line_1.csv'  

into table subway partition (linep = 'line_1');


load data local inpath '/home/hadoop/sampledata/subway_line_2.csv' 

into table subway partition (linep = 'line_2');


load data local inpath '/home/hadoop/sampledata/subway_line_3.csv' 

into table subway partition (linep = 'line_3');


load data local inpath '/home/hadoop/sampledata/subway_line_4.csv' 

into table subway partition (linep = 'line_4');
```



-데이터가 들어 왔는지 확인 하는 방법 (1. 쿼리문으로 조회.)

```sql
select * from subway;
select * from subway where linep='line_1'; <---where linep='line_2~4'는 생략;
```



-데이터가 들어 왔는지 확인 하는 방법 (2. 쿼리문으로 조회.)

```sql
hadoop fs -ls /user/hive/warehouse/mydb.db/subway/ 
(hadoop fs를 hdfs dfs도 가능)

hadoop fs -cat /user/hive/warehouse/mydb.db/subway/linep=line_1/subway_line_1.csv
```



cf.참고 

[ HIVE 클라이언트 구현시 pom.xml 에 넣어야 하는 내용 ] 

```xml
<dependency>

	<groupId>org.apache.hive</groupId>

	<artifactId>hive-jdbc</artifactId>

	<version>2.3.5</version>

</dependency>
```