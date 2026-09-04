#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[distance=..3,tag=spell,tag=reflector] run function items:semblanceofdoom/__generated__/block/73
execute at @s if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function items:semblanceofdoom/__generated__/block/75
tag @e[tag=spell,tag=reflector] remove matchedid
execute at @s run function items:semblanceofdoom/__generated__/block/78