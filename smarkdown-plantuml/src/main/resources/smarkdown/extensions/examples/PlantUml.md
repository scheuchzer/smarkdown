# PlantUml Examples


This page contains some examples of some of the diagrams that PlantUml can render. 


<div class='alert alert-info'>
This is an **incomplete** list of the diagrams that can be created with PlantUml!
</div>


## Class diagram

	```plant
	@startuml
	class BaseClass
	namespace net.dummy #DDDDDD {
	    .BaseClass <|-- Person
	    Meeting o-- Person
	    
	    .BaseClass <|- Meeting
	}
	
	namespace net.foo {
	  net.dummy.Person  <|- Person
	  .BaseClass <|-- Person
	
	  net.dummy.Meeting o-- Person
	}
	
	BaseClass <|-- net.unused.Person
	@enduml
	```


```plant
@startuml
class BaseClass
namespace net.dummy #DDDDDD {
    .BaseClass <|-- Person
    Meeting o-- Person
    
    .BaseClass <|- Meeting
}

namespace net.foo {
  net.dummy.Person  <|- Person
  .BaseClass <|-- Person

  net.dummy.Meeting o-- Person
}

BaseClass <|-- net.unused.Person
@enduml
```


## Activity diagram

	```plant
	@startuml
	title this is my title
	if (condition?) then (yes)
	  :yes;
	else (no)
	  :no;
	  note right
	    this is a note
	  end note
	endif
	stop
	
	legend
	this is the legend
	endlegend
	
	footer dummy footer
	header
	  this is
	  a long __dummy__ header
	end header
	@enduml
	```


```plant
@startuml
title this is my title
if (condition?) then (yes)
  :yes;
else (no)
  :no;
  note right
    this is a note
  end note
endif
stop

legend
this is the legend
endlegend

footer dummy footer
header
  this is
  a long __dummy__ header
end header

@enduml
```


## Component diagram

	```plant
	@startuml
	
	interface "Data Access" as DA
	
	DA - [First Component] 
	[First Component] ..> HTTP : use
	
	note left of HTTP : Web Service only
	
	note right of [First Component]
	  A note can also
	  be on several lines
	end note
	@enduml
	```


```plant
@startuml
interface "Data Access" as DA

DA - [First Component] 
[First Component] ..> HTTP : use

note left of HTTP : Web Service only

note right of [First Component]
  A note can also
  be on several lines
end note
@enduml
```


## Use case diagram

	```plant
	@startuml
	left to right direction
	skinparam packageStyle rect
	actor customer
	actor clerk
	rectangle checkout {
	  customer -- (checkout)
	  (checkout) .> (payment) : include
	  (help) .> (checkout) : extends
	  (checkout) -- clerk
	}
	@enduml
	```


```plant
@startuml
left to right direction
skinparam packageStyle rect
actor customer
actor clerk
rectangle checkout {
  customer -- (checkout)
  (checkout) .> (payment) : include
  (help) .> (checkout) : extends
  (checkout) -- clerk
}
@enduml
```


## Sequence diagram

	```plant
	@startuml
	
	skinparam backgroundColor #EEEBDC
	
	actor 使用者
	participant "頭等艙" as A
	participant "第二類" as B
	participant "最後一堂課" as 別的東西
	
	使用者 -> A: 完成這項工作
	activate A
	
	A -> B: 創建請求
	activate B
	
	B -> 別的東西: 創建請求
	activate 別的東西
	別的東西 --> B: 這項工作完成
	destroy 別的東西
	
	B --> A: 請求創建
	deactivate B
	
	A --> 使用者: 做完
	deactivate A
	@enduml
	```


```plant
@startuml

skinparam backgroundColor #EEEBDC

actor 使用者
participant "頭等艙" as A
participant "第二類" as B
participant "最後一堂課" as 別的東西

使用者 -> A: 完成這項工作
activate A

A -> B: 創建請求
activate B

B -> 別的東西: 創建請求
activate 別的東西
別的東西 --> B: 這項工作完成
destroy 別的東西

B --> A: 請求創建
deactivate B

A --> 使用者: 做完
deactivate A
@enduml
```


## GUI wireframe

	```plant
	@startsalt
	{+
	{* File | Edit | Source | Refactor }
	{/ General | Fullscreen | Behavior | Saving }
	{
		{ Open image in: | ^Smart Mode^ }
		[X] Smooth images when zoomed
		[X] Confirm image deletion
		[ ] Show hidden images 
	}
	[Close]
	}
	@endsalt
	```


```plant
@startsalt
{+
{* File | Edit | Source | Refactor }
{/ General | Fullscreen | Behavior | Saving }
{
	{ Open image in: | ^Smart Mode^ }
	[X] Smooth images when zoomed
	[X] Confirm image deletion
	[ ] Show hidden images 
}
[Close]
}
@endsalt
```


## Sudoku

	```plant
	@startuml
	sudoku
	@enduml
	```


```plant
@startuml
sudoku
@enduml
```