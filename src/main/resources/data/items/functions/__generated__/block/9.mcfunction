#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
execute as @a run function items:__generated__/block/10
scoreboard players add @s timer 1
execute as @s[scores={timer=50..}] run tag @s add end
execute at @s[tag=!end] run function items:semblanceofdoom/slowcast
execute at @s[tag=!end] run function items:semblanceofdoom/slowcast
execute at @s[tag=!end] run function items:semblanceofdoom/slowcast
execute as @s[tag=end] at @s run function items:__generated__/block/11
tag @a remove UUIDMatch