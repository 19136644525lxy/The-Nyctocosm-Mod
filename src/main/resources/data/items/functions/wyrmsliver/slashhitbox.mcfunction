#built using mc-build (https://github.com/mc-build/mc-build)

particle dust 1 0 0 0.75 ~ ~ ~ 0.2 0.2 0.2 0 2 force
particle dust 0 0 0 0.75 ^ ^ ^-1 0.2 0.2 0.2 0 1 force
tag @a[tag=UUIDMatch] add damagetemp
execute unless block ~ ~ ~ #nyctocosm:passthrough run kill @s
execute positioned ~-0.5 ~-0.5 ~-0.5 as @e[type=#nyctocosm:livingthings,tag=!UUIDMatch,dx=0,tag=!sliverhit] at @s run function items:wyrmsliver/__generated__/block/39
tag @a remove damagetemp
execute positioned ~-0.5 ~-0.5 ~-0.5 if entity @e[type=#nyctocosm:livingthings,tag=!UUIDMatch,dx=0] run kill @s