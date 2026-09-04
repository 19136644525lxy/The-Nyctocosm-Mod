#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation TEMP s.circle_timer = 0 value
execute rotated ~ 0 run function ajmobilespellplane:summon
execute as @e[tag=aj.ajmobilespellplane.root,tag=!summoned] run function spells:ring/__generated__/block/539
scoreboard players reset TEMP s.circle_timer