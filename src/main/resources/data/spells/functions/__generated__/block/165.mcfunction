#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score UUID0 value run data get entity @s UUID[0]
execute store result score UUID1 value run data get entity @s UUID[1]
execute store result score UUID2 value run data get entity @s UUID[2]
execute store result score UUID3 value run data get entity @s UUID[3]
execute as @e[tag=tendril] run function spells:__generated__/block/166
execute at @s rotated ~ 0 positioned ^1 ^ ^ facing entity @e[tag=tendril,tag=matchedid,limit=1] feet run function spells:submergence/raycast
execute at @s rotated ~ 0 positioned ^-1 ^1 ^ facing entity @e[tag=tendril,tag=matchedid,limit=1] feet run function spells:submergence/raycast
execute at @s rotated ~ 0 positioned ^ ^0.5 ^1 facing entity @e[tag=tendril,tag=matchedid,limit=1] feet run function spells:submergence/raycast
execute at @s rotated ~ 0 positioned ^ ^1 ^ facing entity @e[tag=tendril,tag=matchedid,limit=1] feet run function spells:submergence/raycast
scoreboard players add @s s.submergence_timer 1
effect give @s slowness 1 10 true
execute as @s[scores={s.submergence_timer=1..80}] run function spells:__generated__/block/168
execute as @s[scores={s.submergence_timer=81}] run function spells:__generated__/block/169
execute as @s[scores={s.submergence_timer=81..}] run function spells:__generated__/block/175
tag @e[tag=matchedid] remove matchedid