for /l %%r in (10, 10, 100) do (
	jmeter-n.cmd Simplemicro.jmx -Jathletics=%%r
)