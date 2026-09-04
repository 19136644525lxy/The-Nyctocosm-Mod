#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s facing entity @a[gamemode=!spectator,tag=!emissarydebug,limit=1,sort=random,distance=..300] eyes run tp @s ~ ~ ~ ~ ~
execute at @s run function effects:emissary/wingeye