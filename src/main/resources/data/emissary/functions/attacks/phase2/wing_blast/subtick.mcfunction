#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=!blasting] at @s run function emissary:attacks/phase2/wing_blast/__generated__/block/347
execute as @s[tag=blasting] at @s run function emissary:attacks/phase2/wing_blast/__generated__/block/348
kill @s[scores={em.timer=43..}]