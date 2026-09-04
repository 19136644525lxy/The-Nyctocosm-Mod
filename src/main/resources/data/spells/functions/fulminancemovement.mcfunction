#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ^ ^ ^1.5
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough unless block ^ ^ ^-1 #nyctocosm:passthrough run tag @s add hit
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[distance=..3,tag=spell,tag=reflector] run function spells:__generated__/block/143
execute at @s if entity @e[tag=spell,tag=!matchedid,tag=reflector,distance=..3] run function spells:__generated__/block/145
tag @e[tag=spell,tag=reflector,distance=..3] remove matchedid
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 run scoreboard players operation @e[type=#nyctocosm:livingthings,dx=0] ncd_damage = @s s.damage
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0] at @s run function spells:__generated__/block/148
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 if entity @e[type=#nyctocosm:livingthings,dx=0] run function spells:__generated__/block/149