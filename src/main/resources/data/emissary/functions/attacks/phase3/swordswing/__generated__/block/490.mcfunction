#built using mc-build (https://github.com/mc-build/mc-build)

data merge entity @s {width:4f,height:0.8f,response:1b,Tags:["emissary","attack","hitregion","swordswing"]}
tp @s ~ ~ ~ ~ ~
scoreboard players set @s em.timer 5
playsound nyctocosm:emissary.attack.sword_swing master @a[distance=..30] ~ ~ ~ 2