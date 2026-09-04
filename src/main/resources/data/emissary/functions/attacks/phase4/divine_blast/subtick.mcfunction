#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=shooter,tag=activated] at @s run function emissary:attacks/phase4/divine_blast/__generated__/block/715
scoreboard players add @s em.value 1
kill @s[scores={em.value=150..}]