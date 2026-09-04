#built using mc-build (https://github.com/mc-build/mc-build)

execute if score @s submergencedeath matches 1.. run function spells:__generated__/block/185
scoreboard players add @s s.submergence_timer 1
tag @s[scores={s.submergence_timer=200..}] remove s.submerging