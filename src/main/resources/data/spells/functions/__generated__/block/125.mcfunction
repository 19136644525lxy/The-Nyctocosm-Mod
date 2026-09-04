#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
execute at @s run tp @s ^ ^ ^2
execute as @s[scores={m.spell.power=..5}] run function spells:__generated__/block/126
execute as @s[scores={m.spell.power=6..}] run function spells:__generated__/block/127
execute as @s[scores={m.spell.power=..5}] run fill ~-6 ~-6 ~-6 ~6 ~6 ~6 minecraft:air replace #nyctocosm:vacable
execute as @s[scores={m.spell.power=6..}] run fill ~-10 ~-10 ~-10 ~10 ~10 ~10 minecraft:air replace #nyctocosm:vacable
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[distance=..3,tag=spell,tag=reflector] run function spells:__generated__/block/128
execute at @s if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function spells:__generated__/block/130
tag @e[tag=spell,tag=reflector] remove matchedid
execute as @a[distance=..10] run function spells:__generated__/block/133
execute at @s[scores={m.spell.power=..5}] as @e[type=#nyctocosm:manipulable,distance=..6,tag=!matchedid,tag=!s.immoveable] run function spells:__generated__/block/135
execute at @s[scores={m.spell.power=6..}] as @e[type=#nyctocosm:manipulable,distance=..10,tag=!matchedid,tag=!s.immoveable] run function spells:__generated__/block/138
tag @a remove matchedid
execute if score @s spelltimer matches ..0 run function spells:__generated__/block/141