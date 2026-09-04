#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
tp @s ^ ^ ^1.5
execute if score @s spelltimer matches ..0 run kill @s
function effects:nightmare_vein/singleshort
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough unless block ^ ^ ^-1 #nyctocosm:passthrough run kill @s
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[distance=..3,tag=spell,tag=reflector] run function spells:__generated__/block/50
execute at @s if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function spells:__generated__/block/52
tag @e[tag=spell,tag=reflector] remove matchedid
function damage:playermarkertag
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 run scoreboard players operation @e[type=#nyctocosm:livingthings,dx=0] ncd_damage = @s s.damage
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0] at @s run function spells:__generated__/block/55
tag @a remove damagetemp
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 if entity @e[type=#nyctocosm:livingthings,dx=0] run kill @s