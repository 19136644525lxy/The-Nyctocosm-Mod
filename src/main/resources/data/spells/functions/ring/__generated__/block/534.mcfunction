#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TEMP s.circle_timer = 0 value
execute rotated ~ 0 run function ajspellplane:summon
execute as @e[tag=aj.ajspellplane.root,tag=!summoned] run function spells:ring/__generated__/block/535
scoreboard players reset TEMP s.circle_timer