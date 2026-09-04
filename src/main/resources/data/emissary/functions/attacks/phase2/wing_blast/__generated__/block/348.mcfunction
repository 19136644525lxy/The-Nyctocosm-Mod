#built using mc-build (https://github.com/mc-build/mc-build)

particle flash ~ ~ ~ 0 0 0 0 1 force
scoreboard players add @s em.timer 1
execute as @s[tag=rightupper] at @s run tp @s ~ ~ ~ ~0.3 ~-0.3
execute as @s[tag=leftupper] at @s run tp @s ~ ~ ~ ~-0.3 ~-0.3
execute as @s[tag=rightlower] at @s run tp @s ~ ~ ~ ~0.3 ~0.3
execute as @s[tag=leftlower] at @s run tp @s ~ ~ ~ ~-0.3 ~0.3
execute as @s[tag=rightmiddle] at @s run tp @s ~ ~ ~ ~0.3 ~
execute as @s[tag=leftmiddle] at @s run tp @s ~ ~ ~ ~-0.3 ~
execute at @s run function emissary:attacks/phase2/wing_blast/raycast