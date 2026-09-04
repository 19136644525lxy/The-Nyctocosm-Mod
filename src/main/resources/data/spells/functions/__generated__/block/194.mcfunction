#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TEMP s.circle_timer = 0 value
execute rotated ~ 0 run function ajspellplane:summon
execute as @e[tag=aj.ajspellplane.root,tag=!summoned] run function spells:__generated__/block/195
scoreboard players reset TEMP s.circle_timer