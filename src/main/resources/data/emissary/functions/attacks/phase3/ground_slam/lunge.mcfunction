#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @a[gamemode=!spectator,tag=!emissarydebug,distance=..1.5] run tag @s add stoplunge
execute at @s run tp @s[tag=!stoplunge] ^ ^ ^0.8
tag @s remove stoplunge