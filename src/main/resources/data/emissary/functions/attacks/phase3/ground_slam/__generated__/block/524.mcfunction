#built using mc-build (https://github.com/mc-build/mc-build)

data merge entity @s {width:3f,height:0.1f,response:0b,Tags:["emissary","attack","hitregion","unparriable","ground_slam"]}
tp @s ~ ~ ~ ~ ~
scoreboard players set @s em.timer 5
playsound nyctocosm:emissary.attack.stomp master @a[distance=..30] ~ ~ ~ 2