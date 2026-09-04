#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
execute as @a run function items:__generated__/block/6
scoreboard players add @s timer 1
execute as @s[scores={timer=50..}] run kill @s
tp @s ^ ^ ^1
execute as @s[tag=!grounded] run function items:__generated__/block/7
execute as @s[tag=grounded] run function items:__generated__/block/8
tag @a remove UUIDMatch