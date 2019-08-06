## For Hive_semi_Project Reference



1.Apache_Hive를 수행함에 있어, 데이터 전체를 한번에 저장하는것도 좋지만,

속도 향상을 위해서는 전체 데이터에 대해 파티션(partition)별로 

데이터를 나눠서 다루는게 좋다.

ex) subway.html의 경우 (지하철 노선별) (Column = Line , Time , Ride , TakeOff)

즉 1호선,2호선,3호선,4호선---등등 이렇게 파티션별로 데이터를 나눠서 다루는게 좋다.

ex) 10BLOCK이 있을때 , 하나씩 1B,2B,2B,3B,로 연도별로(2012~2015)로 나눠서 저장한다.

거기에 나눠서 또 서브파티션으로 (월단위로) 세분화 시켜서 저장한다.



2.SpringMVC에서 Hive 연결 방식.

1.pom.xml에 다음과 같은 dependency를 설정함으로써,

스프링 컨테이너가 톰캣 서버 기동할떄 미리 hive에 접속해서 접속에 필요한 객체를 받는다.

```xml
<dependency>
    <groupId>org.apache.hive</groupId>
	<artifactId>hive-jdbc</artifactId>
	<version>2.3.5</version>
</dependency>
```



2.받은 객체를 datasource에 생성 해놓는다.(servlet-context.xml에 beans에 미리 생성을해놓음)driver접속과 정보를 이 내용들을 반영해서 미리 생성해놓는다.

``` xml
   
   <beans:bean id="hiveDriver"
		class="org.apache.hive.jdbc.HiveDriver" />
	<beans:bean id="hiveDataSource"
		class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
		<beans:constructor-arg name="driver"
			ref="hiveDriver" />
		<beans:constructor-arg name="url"
			value="jdbc:hive2://192.168.111.120:10000/mydb" />
		<beans:constructor-arg name="username"
			value="root" />
		<beans:constructor-arg name="password"
			value="password" />
	</beans:bean>
	<!-- <beans:bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource"> 
		<beans:property name="driverClassName" value="oracle.jdbc.OracleDriver" /> 
		<beans:property name="url" value="jdbc:oracle:thin:@localhost:1521:XE" /> 
		<beans:property name="username" value="jdbctest" /> <beans:property name="password" 
		value="jdbctest" /> </beans:bean> <beans:bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
		<beans:property name="dataSource" ref="dataSource" /> <beans:property name="configLocation" 
		value="classpath:/mybatis-config.xml" /> <beans:property name="mapperLocations" 
		value="classpath:/*Mapper.xml" /> </beans:bean> <beans:bean id="sqlSession" 
		class="org.mybatis.spring.SqlSessionTemplate" destroy-method="clearCache"> 
		<beans:constructor-arg index="0" ref="sqlSessionFactory"></beans:constructor-arg> 
		</beans:bean> -->

```



3.컨넥션 객체를 미리 생성해서  오토와이어링해서준다. 오라클이 제대로 기동하고있으면

(하이브에 접속하는 데이터소스객체도있고, 오라클에 접속하는 데이터소스 객체도 있어서

중복하는게 있으니, @Qualifier을 사용해서 어떤 데이터소스를 가져다 쓰겠다 라고 정의를함.)

즉 bean태그의 id의 이름인 hiveDataSource을 @Qualifier의 이름으로 해준다.

```java
package dao;
//import 생략
@Repository
public class SubwayDAO {
	@Autowired
	@Qualifier("hiveDataSource")	
	DataSource ds; //<--hive connection객체
	
	public List<SubwayVO> select1(int i) {
		System.out.println("select1의 i값 :"+i);
		List<SubwayVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {conn = ds.getConnection();//<--autowired된 ds객체로부터 다시 정보를 꺼내온다.
			pstmt = conn.prepareStatement("select *from subway where 		   line='line_"+i+"'");//이하 생략
```



4.zipcode.csv 및 oneperson을 위한 Hive 테이블 생성 및 파티션 쿼리문.

주의점! set hive.exec.dynamic.partition.mode=nonstrict;  <--- 파티션의 동적할당을 위해서 

4-1.zipcode

```sql
create table postinfo(postNum string, area1 string, area2 string, area3 string, area4 string, id int) row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

load data local inpath '/root/sampledata/zipcode.csv' into table postinfo;

set hive.exec.dynamic.partition.mode=nonstrict; 

insert into table postinfo3 partition(area1) select postNum, area2, area3, area4, id, area1 from postinfo;

show partitions postinfo3;
```



4-2.oneperson

```sql
create table oneperson3(gu string, dong string, onePerson int) row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

load data local inpath '/root/sampledata/one_person_households.csv' into table oneperson3

create table oneperson(dong string, onePerson int) partitioned by (gu string)row format delimited fields terminated by ',' lines terminated by '\n' stored as textfile;

set hive.exec.dynamic.partition.mode=nonstrict;
insert into table oneperson partition(gu) select dong, onePerson, gu from oneperson3;
```