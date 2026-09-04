#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add FissureRange value 1
execute rotated ~ ~-90 run function effects:sorcerer/halobeambeam
execute if score FissureRange value matches 40.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add endraycast
tag @s add caster
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,dx=0,tag=!caster,tag=!fissurehit] at @s run function items:nullfissure/__generated__/block/53
tag @s remove caster
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run function items:nullfissure/__generated__/block/59
execute as @s[tag=!endraycast] unless entity @e[tag=reflector,tag=!matchedid,tag=spell,distance=..3] positioned ^ ^ ^1 run function items:nullfissure/raycast
execute as @s[tag=!endraycast] rotated as @e[tag=reflector,tag=spell,tag=!matchedid,distance=..3,limit=1,sort=nearest] positioned ^ ^ ^1 run function items:nullfissure/raycast
execute as @s[tag=endraycast] run function items:nullfissure/__generated__/block/61