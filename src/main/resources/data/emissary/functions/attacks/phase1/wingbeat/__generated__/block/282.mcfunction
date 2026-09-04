#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~ ~ 1 1 1 0 5 force
particle flash ~ ~ ~ 1 1 1 0 5 force
execute at @s run playsound nyctocosm:emissary.attack.wingbeat.impact master @a[distance=..300] ~ ~ ~ 50
effect give @a[distance=..5,gamemode=!spectator,tag=!emissarydebug,tag=!s.immoveable] levitation 1 30 true