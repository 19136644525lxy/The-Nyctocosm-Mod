#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s facing entity @e[tag=matchedid,limit=1] feet run tp @s ^ ^ ^1
effect give @s levitation 1 128 true
execute if entity @e[tag=matchedid,limit=1,distance=..1] run function spells:__generated__/block/176