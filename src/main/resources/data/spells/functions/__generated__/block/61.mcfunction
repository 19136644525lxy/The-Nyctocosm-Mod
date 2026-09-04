#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
scoreboard players add @s timer 1
tp @s ^ ^ ^1.2
execute if score @s spelltimer matches ..0 run kill @s
execute rotated ~ ~90 run function effects:spells/special/forbiddance
execute if entity @e[tag=portal,distance=..3] run function spells:__generated__/block/62
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[tag=spell,distance=..5] run function spells:__generated__/block/63
execute if score @s timer matches 3.. at @s if entity @e[tag=spell,tag=!matchedid,distance=0.01..5] run scoreboard players remove @s s.forbiddance_pierce 1
execute if score @s s.forbiddance_pierce matches ..-1 run tag @s add countered
execute if score @s timer matches 3.. at @s as @e[tag=spell,tag=!matchedid,distance=0.01..5] at @s run function spells:__generated__/block/65
tag @e[tag=spell] remove matchedid
kill @s[tag=countered]