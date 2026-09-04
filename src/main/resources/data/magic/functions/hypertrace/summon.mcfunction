#built using mc-build (https://github.com/mc-build/mc-build)

function magic:hypertrace/assignpid
execute positioned ~ ~1 ~ rotated ~ 0 run function animated_java:ajhypertrace/summon
execute as @e[tag=aj.ajhypertrace.root,tag=!hypertrace] run function magic:hypertrace/__generated__/block/12
execute positioned ~ ~0.65 ~ summon interaction run function magic:hypertrace/__generated__/block/16