#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run data get entity @s UUID[0]
execute store result score UUID1 value run data get entity @s UUID[1]
execute store result score UUID2 value run data get entity @s UUID[2]
execute store result score UUID3 value run data get entity @s UUID[3]
execute at @s as @e[tag=spell,tag=reflector] run function items:nullfissure/__generated__/block/51
tag @s add damagetemp
execute at @s anchored eyes positioned ^ ^ ^2 run function items:nullfissure/raycast
tag @s remove damagetemp
tag @e remove fissurehit
tag @a remove fissurehit
scoreboard players set @s fissurecastcooldown 25
effect give @s slowness 1 1 true
execute at @s positioned ~ ~1.625 ~ positioned ^ ^ ^0.5 run particle flash ~ ~ ~ 0 0 0 0 1 force
execute at @s run playsound nyctocosm:sorcerer.halobeam.shoot master @a[distance=..30] ~ ~ ~ 5