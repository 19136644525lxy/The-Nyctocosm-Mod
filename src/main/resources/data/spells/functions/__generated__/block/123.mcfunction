#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s s.repellance_timer 1
particle cloud ~ ~ ~ 0.1 0.1 0.1 0.3 3 force
execute as @s[scores={s.repellance_timer=26..}] run function spells:__generated__/block/124